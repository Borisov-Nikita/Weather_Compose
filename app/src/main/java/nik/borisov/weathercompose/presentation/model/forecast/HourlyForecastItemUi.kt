package nik.borisov.weathercompose.presentation.model.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HourlyForecastItemUi(

    val time: String,
    @StringRes val conditionDescription: Int,
    @DrawableRes val conditionIcon: Int,
    val temp: String,
    val humidity: String,
    val apparentTemp: String,
    val precipitationProbability: String,
    val windSpeed: String,
    val windDirection: String,
    val windGusts: String,
)
