package nik.borisov.weathercompose.presentation.screen.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.presentation.screen.dialog.LoadingDialog
import nik.borisov.weathercompose.presentation.screen.dialog.PermissionDialog
import nik.borisov.weathercompose.presentation.screen.dialog.ServiceDialog

@Composable
fun BaseScreenStateWithUseServiceDisplay(
    modifier: Modifier = Modifier,
    state: BaseScreenStateWithUseService,
    onErrorGot: (String) -> Unit
) {
    when (state) {
        is BaseScreenStateWithUseService.Initial -> {}

        is BaseScreenStateWithUseService.PermissionRequest -> {
            PermissionDialog(
                permission = state.permission,
                onPermissionGranted = {
                    state.onPermissionResponse(true)
                },
                onPermissionDenied = {
                    state.onPermissionResponse(false)
                },
                modifier = modifier
            )
        }

        is BaseScreenStateWithUseService.ServiceRequest -> {
            ServiceDialog(
                service = state.service,
                onConfirm = {
                    state.onUserResponse(true)
                },
                onDismiss = {
                    state.onUserResponse(false)
                },
                modifier = modifier
            )
        }

        is BaseScreenStateWithUseService.Loading -> {
            LoadingDialog(
                title = R.string.determine_current_location_dialog_title,
                description = R.string.determine_current_location_dialog_description,
                image = R.drawable.ic_location_determination,
                onCanceled = state.onCanceled,
                modifier = modifier
            )
        }

        is BaseScreenStateWithUseService.Error -> {
            onErrorGot(stringResource(id = state.errorMessage))
            state.onMessageDisplayed()
        }
    }
}