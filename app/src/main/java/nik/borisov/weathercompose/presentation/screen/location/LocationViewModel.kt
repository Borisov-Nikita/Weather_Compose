package nik.borisov.weathercompose.presentation.screen.location

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.usecase.location.AddLocationUseCase
import nik.borisov.weathercompose.domain.usecase.location.DeleteLocationUseCase
import nik.borisov.weathercompose.domain.usecase.location.DoNotUseCurrentLocationUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetAutoCompleteLocationFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetCurrentLocationFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetLocationsFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.SearchLocationAsAutoCompleteUseCase
import nik.borisov.weathercompose.domain.usecase.location.UseCurrentLocationUseCase
import nik.borisov.weathercompose.extension.mergeWith
import nik.borisov.weathercompose.presentation.mapper.UiMapper
import nik.borisov.weathercompose.presentation.screen.base.BaseViewModelWithCurrentLocation
import nik.borisov.weathercompose.presentation.util.LocationServiceUtil
import nik.borisov.weathercompose.presentation.util.NetworkServiceUtil
import nik.borisov.weathercompose.util.DataResult
import nik.borisov.weathercompose.util.DateTimeUtil
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    getCurrentLocationFlowUseCase: GetCurrentLocationFlowUseCase,
    getLocationsFlowUseCase: GetLocationsFlowUseCase,
    getAutoCompleteLocationFlowUseCase: GetAutoCompleteLocationFlowUseCase,
    useCurrentLocationUseCase: UseCurrentLocationUseCase,
    private val doNotUseCurrentLocationUseCase: DoNotUseCurrentLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val searchLocationAsAutoCompleteUseCase: SearchLocationAsAutoCompleteUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    locationServiceUtil: LocationServiceUtil,
    networkServiceUtil: NetworkServiceUtil,
    dateTimeUtil: DateTimeUtil
) : BaseViewModelWithCurrentLocation(
    getCurrentLocationFlowUseCase = getCurrentLocationFlowUseCase,
    useCurrentLocationUseCase = useCurrentLocationUseCase,
    locationServiceUtil = locationServiceUtil,
    networkServiceUtil = networkServiceUtil,
    dateTimeUtil = dateTimeUtil
) {

    private val mapper = UiMapper()

    private val locationFlow = getLocationsFlowUseCase()
        .map { locationItemList ->
            locationItemList.map { item ->
                mapper.mapLocationItemToUi(item)
            }
        }
    private val autoCompleteFlow = getAutoCompleteLocationFlowUseCase()

    private var autoCompleteLocationCash = listOf<LocationItem>()

    private val _screenState = MutableStateFlow(
        LocationScreenState.Location(
            locations = listOf(),
            useCurrentLocation = true,
            autoCompleteLocation = listOf(),
            error = "",
        )
    )
    val screenState = combine(
        locationFlow,
        currentLocationFlow
    ) { locationItemList, currentLocation ->
        _screenState.updateAndGet { currentState ->
            currentState.copy(
                locations = locationItemList,
                useCurrentLocation = currentLocation.isCurrentLocationUsed,
                error = ""
            )
        }
    }.mergeWith(
        autoCompleteFlow
            .map { dataResult ->
                handleDataResult(dataResult)
            }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LocationScreenState.Initial
    )

    fun deleteLocation(locationId: Int) {
        viewModelScope.launch {
            if (locationId == LocationItem.CURRENT_LOCATION_ID) {
                doNotUseCurrentLocationUseCase()
            } else {
                deleteLocationUseCase(locationId)
            }
        }
    }

    fun searchAutoCompleteLocation(query: String) {
        viewModelScope.launch {
            checkNetworkService { isEnabled ->
                if (isEnabled) {
                    searchLocationAsAutoCompleteUseCase(query)
                }
            }
        }
    }

    fun addLocation(locationId: Int) {
        viewModelScope.launch {
            addLocationUseCase(
                autoCompleteLocationCash.first { locationItem ->
                    locationItem.id == locationId
                }
            )
        }
    }

    fun useCurrentLocation() {
        viewModelScope.launch {
            getCurrentLocation(
                onCurrentLocationGot = { }
            )
        }
    }

    private fun handleDataResult(dataResult: DataResult<List<LocationItem>>): LocationScreenState.Location {
        return _screenState.updateAndGet { currentState ->
            when (dataResult) {
                is DataResult.Success -> {
                    autoCompleteLocationCash = dataResult.data!!
                    currentState.copy(
                        autoCompleteLocation = autoCompleteLocationCash.map { locationItem ->
                            mapper.mapLocationItemToUi(locationItem)
                        },
                        error = ""
                    )
                }

                is DataResult.Failure -> {
                    currentState.copy(
                        autoCompleteLocation = listOf(),
                        error = dataResult.message ?: ""
                    )
                }
            }
        }
    }
}