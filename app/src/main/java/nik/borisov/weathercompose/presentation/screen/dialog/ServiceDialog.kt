package nik.borisov.weathercompose.presentation.screen.dialog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nik.borisov.weathercompose.presentation.util.ServicesToEnable

@Composable
fun ServiceDialog(
    modifier: Modifier = Modifier,
    service: ServicesToEnable,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            onConfirm()
        }
    )

    CommonDialog(
        title = service.title,
        description = service.description,
        image = service.image,
        isConfirmUse = true,
        onConfirm = { resultLauncher.launch(service.intent) },
        onDismiss = onDismiss,
        modifier = modifier
    )
}