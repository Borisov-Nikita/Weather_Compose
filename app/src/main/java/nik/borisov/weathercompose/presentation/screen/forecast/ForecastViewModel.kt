package nik.borisov.weathercompose.presentation.screen.forecast

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.usecase.forecast.GetForecastFlowUseCase
import nik.borisov.weathercompose.domain.usecase.forecast.UpdateForecastUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetCurrentLocationFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetLocationsFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.UseCurrentLocationUseCase
import nik.borisov.weathercompose.domain.usecase.settings.GetSettingsFlowUseCase
import nik.borisov.weathercompose.extension.mergeWith
import nik.borisov.weathercompose.presentation.mapper.UiMapper
import nik.borisov.weathercompose.presentation.screen.base.BaseViewModelWithCurrentLocation
import nik.borisov.weathercompose.presentation.util.LocationServiceUtil
import nik.borisov.weathercompose.presentation.util.NetworkServiceUtil
import nik.borisov.weathercompose.util.DateTimeUtil
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    getCurrentLocationFlowUseCase: GetCurrentLocationFlowUseCase,
    getLocationsFlowUseCase: GetLocationsFlowUseCase,
    useCurrentLocationUseCase: UseCurrentLocationUseCase,
    getForecastsFlowUseCase: GetForecastFlowUseCase,
    private val updateForecastUseCase: UpdateForecastUseCase,
    getSettingsFlowUseCase: GetSettingsFlowUseCase,
    locationServiceUtil: LocationServiceUtil,
    networkServiceUtil: NetworkServiceUtil,
    private val dateTimeUtil: DateTimeUtil
) : BaseViewModelWithCurrentLocation(
    getCurrentLocationFlowUseCase = getCurrentLocationFlowUseCase,
    useCurrentLocationUseCase = useCurrentLocationUseCase,
    locationServiceUtil = locationServiceUtil,
    networkServiceUtil = networkServiceUtil,
    dateTimeUtil = dateTimeUtil
) {

    private val mapper = UiMapper()

    private val dataResultFlow = getForecastsFlowUseCase()

    private val locationFlow = getLocationsFlowUseCase()

    private val settingsFlow = getSettingsFlowUseCase()

    private var viewModelState = ForecastViewModelState()

    private val _screenState = MutableSharedFlow<ForecastScreenState>()
    val screenState = combineTransform(
        dataResultFlow,
        locationFlow,
        settingsFlow
    ) { forecastList, locationList, settings ->
        if (viewModelState.isInitial) {
            viewModelState = viewModelState.copy(
                forecast = forecastList,
                location = locationList,
                settings = settings,
                isInitial = false
            )
            handleViewModelState()
        } else {
            if (viewModelState.location != locationList) {
                val differenceList = locationList.filterNot {
                    viewModelState.location.contains(it)
                }
                viewModelState = viewModelState.copy(
                    forecast = forecastList,
                    location = locationList
                )
                if (differenceList.isNotEmpty()) {
                    updateForecast(differenceList)
                } else {
                    handleViewModelState()
                }
            } else if (viewModelState.settings != settings) {
                viewModelState = viewModelState.copy(
                    settings = settings
                )
                updateForecast(viewModelState.location)
            } else {
                viewModelState = viewModelState.copy(
                    forecast = forecastList
                )
                emitViewModelState()
            }
        }
        emit(Unit)
    }.filterIsInstance<ForecastScreenState>()
        .mergeWith(
            _screenState
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ForecastScreenState.Initial
        )


    fun refreshForecast() {
        updateForecast(viewModelState.location)
    }

    private fun updateForecast(
        locations: List<LocationItem>
    ) {
        viewModelScope.launch {
            updateServiceState { isServiceEnabled ->
                if (isServiceEnabled) {
                    _screenState.emit(
                        ForecastScreenState.Loading
                    )
                    updateForecastUseCase(
                        locations = locations,
                        onForecastUpdated = { isUpdated ->
                            if (!isUpdated) {
                                emitViewModelState()
                                emitErrorToServiceState(
                                    R.string.error_failed_to_update
                                )
                            }
                        }
                    )
                } else {
                    emitViewModelState()
                    emitErrorToServiceState(
                        R.string.error_failed_to_update
                    )
                }
            }
        }
    }

    private suspend fun updateServiceState(
        onServiceEnabled: suspend (Boolean) -> Unit
    ) {
        if (isCurrentLocationUsed) {
            getCurrentLocation { isCurrentLocationGot ->
                if (isCurrentLocationGot) {
                    onServiceEnabled(true)
                } else {
                    checkNetworkService { isNetworkServiceEnabled ->
                        onServiceEnabled(isNetworkServiceEnabled)
                    }
                }
            }
        } else {
            checkNetworkService { isNetworkServiceEnabled ->
                onServiceEnabled(isNetworkServiceEnabled)
            }
        }
    }


    private fun handleViewModelState() {
        if (viewModelState.forecast.isNotEmpty()) {
            val forecastsToUpdate = viewModelState.forecast.filter { forecast ->
                forecast.lastUpdateEpoch < dateTimeUtil.getTimeHalfHourBeforeCurrent()
            }
            if (forecastsToUpdate.isNotEmpty()) {
                val locationsToUpdate = forecastsToUpdate.map { forecastItem ->
                    forecastItem.location
                }
                updateForecast(locationsToUpdate)
            } else {
                emitViewModelState()
            }
        } else {
            updateForecast(viewModelState.location)
        }
    }

    private fun emitViewModelState() {
        viewModelScope.launch {
            _screenState.emit(
                ForecastScreenState.Forecast(
                    forecast = viewModelState.forecast.map { forecastItem ->
                        mapper.mapForecastItemToUi(forecastItem)
                    }
                )
            )
        }
    }
}