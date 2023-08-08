package nik.borisov.weathercompose.presentation.screen.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import nik.borisov.weathercompose.presentation.util.PermissionsToRequest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permission: PermissionsToRequest,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionsState = rememberPermissionState(
        permission = permission.permission
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    CommonDialog(
        title = permission.title,
        description = permission.description,
        image = permission.image,
        isConfirmUse = true,
        onConfirm = { permissionsState.launchPermissionRequest() },
        onDismiss = onPermissionDenied,
        modifier = modifier
    )
}