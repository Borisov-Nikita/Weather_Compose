package nik.borisov.weathercompose.domain.entity.forecast

data class DailyForecastItem(

    val time: Long,
    val tempMax: Int,
    val tempMin: Int,
    val sunrise: Long,
    val sunset: Long,
    val precipitationProbability: Int,
    val weatherCode: Int,
    val windSpeed: Int,
    val windDirection: Int,
    val windGusts: Int
)
