package nik.borisov.weathercompose.presentation.screen.base

import androidx.annotation.StringRes
import nik.borisov.weathercompose.presentation.util.PermissionsToRequest
import nik.borisov.weathercompose.presentation.util.ServicesToEnable

sealed class BaseScreenStateWithUseService {

    object Initial : BaseScreenStateWithUseService()

    data class Loading(
        val onCanceled: () -> Unit
    ) : BaseScreenStateWithUseService()

    data class Error(
        @StringRes val errorMessage: Int,
        val onMessageDisplayed: () -> Unit
    ) : BaseScreenStateWithUseService()

    data class PermissionRequest(
        val permission: PermissionsToRequest,
        val onPermissionResponse: (Boolean) -> Unit
    ) : BaseScreenStateWithUseService()

    data class ServiceRequest(
        val service: ServicesToEnable,
        val onUserResponse: (Boolean) -> Unit
    ) : BaseScreenStateWithUseService()
}