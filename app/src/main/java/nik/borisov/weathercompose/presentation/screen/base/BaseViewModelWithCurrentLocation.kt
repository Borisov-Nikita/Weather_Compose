package nik.borisov.weathercompose.presentation.screen.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.domain.entity.location.CurrentLocationItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.usecase.location.GetCurrentLocationFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.UseCurrentLocationUseCase
import nik.borisov.weathercompose.presentation.util.LocationServiceUtil
import nik.borisov.weathercompose.presentation.util.NetworkServiceUtil
import nik.borisov.weathercompose.presentation.util.PermissionsToRequest
import nik.borisov.weathercompose.presentation.util.ServicesToEnable
import nik.borisov.weathercompose.util.DateTimeUtil
import java.util.TimeZone

abstract class BaseViewModelWithCurrentLocation(
    getCurrentLocationFlowUseCase: GetCurrentLocationFlowUseCase,
    private val useCurrentLocationUseCase: UseCurrentLocationUseCase,
    private val locationServiceUtil: LocationServiceUtil,
    private val networkServiceUtil: NetworkServiceUtil,
    private val dateTimeUtil: DateTimeUtil
) : ViewModel() {

    protected val currentLocationFlow = getCurrentLocationFlowUseCase()
    protected var isCurrentLocationUsed = false

    private val _serviceState = MutableSharedFlow<BaseScreenStateWithUseService>()
    val serviceState = _serviceState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BaseScreenStateWithUseService.Initial
        )

    protected fun emitErrorToServiceState(
        @StringRes errorMessage: Int
    ) {
        viewModelScope.launch {
            _serviceState.emit(
                BaseScreenStateWithUseService.Error(
                    errorMessage = errorMessage,
                    onMessageDisplayed = {
                        viewModelScope.launch {
                            _serviceState.emit(
                                BaseScreenStateWithUseService.Initial
                            )
                        }
                    }
                )
            )
        }
    }

    protected suspend fun getCurrentLocation(
        onCurrentLocationGot: suspend (Boolean) -> Unit
    ) {
        checkServiceToGetCurrentLocation { isEnabled ->
            if (isEnabled) {
                val job = viewModelScope.launch {
                    saveCurrentLocation { isCurrentLocationSaved ->
                        onCurrentLocationGot(isCurrentLocationSaved)
                        if (!isCurrentLocationSaved) {
                            emitErrorToServiceState(
                                R.string.error_define_current_location
                            )
                        } else {
                            setServiceStateToInitial()
                        }
                    }
                }
                _serviceState.emit(
                    BaseScreenStateWithUseService.Loading(
                        onCanceled = {
                            job.cancel()
                            viewModelScope.launch {
                                setServiceStateToInitial()
                                onCurrentLocationGot(false)
                            }
                        }
                    )
                )
            } else {
                onCurrentLocationGot(false)
            }
        }
    }

    private suspend fun checkServiceToGetCurrentLocation(
        onServiceEnabled: suspend (Boolean) -> Unit
    ) {
        checkLocationPermission { isGranted ->
            if (isGranted) {
                checkLocationService { isLocationEnabled ->
                    if (isLocationEnabled) {
                        checkNetworkService { isNetworkEnabled ->
                            onServiceEnabled(isNetworkEnabled)
                        }
                    } else {
                        onServiceEnabled(false)
                    }
                }
            } else {
                onServiceEnabled(false)
            }
        }
    }

    protected suspend fun checkNetworkService(
        onNetworkServiceEnabled: suspend (Boolean) -> Unit
    ) {
        if (!networkServiceUtil.isNetworkServiceEnabled()) {
            _serviceState.emit(
                BaseScreenStateWithUseService.ServiceRequest(
                    service = ServicesToEnable.Network(),
                    onUserResponse = { isConfirm ->
                        viewModelScope.launch {
                            setServiceStateToInitial()
                            onNetworkServiceEnabled(isConfirm)
                        }
                    }
                )
            )
        } else {
            onNetworkServiceEnabled(true)
        }
    }

    private suspend fun checkLocationPermission(
        onPermissionResponse: suspend (Boolean) -> Unit
    ) {
        val permission = PermissionsToRequest.ForegroundFineLocation()
        if (!locationServiceUtil.isPermissionGranted(permission)) {
            _serviceState.emit(
                BaseScreenStateWithUseService.PermissionRequest(
                    permission = permission,
                    onPermissionResponse = { isGranted ->
                        viewModelScope.launch {
                            setServiceStateToInitial()
                            onPermissionResponse(isGranted)
                        }
                    }
                )
            )
        } else {
            onPermissionResponse(true)
        }
    }

    private suspend fun checkLocationService(
        onLocationServiceEnabled: suspend (Boolean) -> Unit
    ) {
        if (!locationServiceUtil.isLocationServiceEnabled()) {
            _serviceState.emit(
                BaseScreenStateWithUseService.ServiceRequest(
                    service = ServicesToEnable.Location(),
                    onUserResponse = { isConfirm ->
                        viewModelScope.launch {
                            setServiceStateToInitial()
                            if (isConfirm) {
                                onLocationServiceEnabled(locationServiceUtil.isLocationServiceEnabled())
                            } else {
                                onLocationServiceEnabled(false)
                            }
                        }
                    }
                )
            )
        } else {
            onLocationServiceEnabled(true)
        }
    }

    private suspend fun saveCurrentLocation(
        onCurrentLocationSaved: suspend (Boolean) -> Unit
    ) {
        val location = locationServiceUtil.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude
        if (latitude != null && longitude != null) {
            useCurrentLocationUseCase(
                currentLocationItem = CurrentLocationItem(
                    isCurrentLocationUsed = true,
                    lastUpdateEpoch = dateTimeUtil.getCurrentTimeEpoch()
                ),
                locationItem = LocationItem(
                    id = LocationItem.CURRENT_LOCATION_ID,


                    //TODO
                    name = "Current location",
                    latitude = latitude.toString(),
                    longitude = longitude.toString(),
                    timeZoneId = TimeZone.getDefault().id
                )
            )
            onCurrentLocationSaved(true)
        } else {
            onCurrentLocationSaved(false)
        }
    }

    private suspend fun setServiceStateToInitial() {
        _serviceState.emit(
            BaseScreenStateWithUseService.Initial
        )
    }
}