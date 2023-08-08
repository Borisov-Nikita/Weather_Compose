package nik.borisov.weathercompose.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nik.borisov.weathercompose.R

sealed class PermissionsToRequest(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val permission: String,
) {

    class ForegroundFineLocation : PermissionsToRequest(
        title = R.string.foreground_location_permission_dialog_title,
        description = R.string.foreground_location_permission_dialog_description,
        image = R.drawable.ic_location,
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    class ForegroundCoarseLocation : PermissionsToRequest(
        title = R.string.foreground_location_permission_dialog_title,
        description = R.string.foreground_location_permission_dialog_description,
        image = R.drawable.ic_location,
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
}