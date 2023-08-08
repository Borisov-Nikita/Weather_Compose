package nik.borisov.weathercompose.presentation.model.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DailyForecastItemUi(

    val dayOfWeek: String,
    val date: String,
    @StringRes val conditionDescription: Int,
    @DrawableRes val conditionIcon: Int,
    val tempMax: String,
    val tempMin: String,
    val sunrise: String,
    val sunset: String,
    val precipitationProbability: String,
    val windSpeed: String,
    val windDirection: String,
    val windGusts: String
)
