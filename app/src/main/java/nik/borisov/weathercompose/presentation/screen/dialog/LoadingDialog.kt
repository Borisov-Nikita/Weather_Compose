package nik.borisov.weathercompose.presentation.screen.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int,
    @DrawableRes image: Int,
    onCanceled: () -> Unit
) {
    CommonDialog(
        title = title,
        description = description,
        image = image,
        isConfirmUse = false,
        onConfirm = { },
        onDismiss = onCanceled,
        modifier = modifier
    )
}