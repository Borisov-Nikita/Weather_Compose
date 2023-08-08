package nik.borisov.weathercompose.presentation.model.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrentWeatherItemUi(

    val time: String,
    val sunrise: String,
    val sunset: String,
    @StringRes val conditionDescription: Int,
    @DrawableRes val conditionIcon: Int,
    val temp: String,
    val apparentTemp: String,
    val tempMax: String,
    val tempMin: String,
    val humidity: String,
    val precipitationProbability: String,
    val windSpeed: String,
    val windDirection: String,
    val windGusts: String,
)
