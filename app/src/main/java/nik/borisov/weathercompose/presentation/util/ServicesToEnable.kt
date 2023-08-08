package nik.borisov.weathercompose.presentation.util

import android.content.Intent
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nik.borisov.weathercompose.R

sealed class ServicesToEnable(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    val intent: Intent
) {

    class Location(
    ) : ServicesToEnable(
        title = R.string.location_service_dialog_title,
        description = R.string.location_service_dialog_description,
        image = R.drawable.ic_location,
        intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    )

    class Network(
    ) : ServicesToEnable(
        title = R.string.network_service_dialog_title,
        description = R.string.network_service_dialog_description,
        image = R.drawable.ic_network,
        intent = Intent(Settings.ACTION_WIFI_SETTINGS)
    )
}
