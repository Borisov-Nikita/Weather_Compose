package nik.borisov.weathercompose.presentation.model.settings

import androidx.annotation.StringRes
import nik.borisov.weathercompose.R

enum class UnitsUi(
    @StringRes val description: Int
) {
    CELSIUS(description = R.string.unit_celsius_description),
    FAHRENHEIT(description = R.string.unit_fahrenheit_description),
    KILOMETER(description = R.string.unit_km_p_h_description),
    MILES(description = R.string.unit_m_p_h_description),
    METERS(description = R.string.unit_m_p_s_description)
}