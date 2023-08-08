package nik.borisov.weathercompose.presentation.navigation

import androidx.annotation.StringRes
import nik.borisov.weathercompose.R

sealed class Screen(
    val route: String,
    @StringRes val name: Int
) {

    object Forecast : Screen(ROUTE_FORECAST, R.string.forecast_title)
    object Location : Screen(ROUTE_LOCATION, R.string.location_title)
    object Settings : Screen(ROUTE_SETTINGS, R.string.settings_title)

    companion object {

        private const val ROUTE_FORECAST = "forecast"
        private const val ROUTE_LOCATION = "location"
        private const val ROUTE_SETTINGS = "settings"
    }
}
