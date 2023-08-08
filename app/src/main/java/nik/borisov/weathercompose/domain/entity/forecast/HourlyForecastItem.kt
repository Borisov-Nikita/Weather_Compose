package nik.borisov.weathercompose.domain.entity.forecast

data class HourlyForecastItem(

    val time: Long,
    val isDay: Boolean,
    val temp: Int,
    val humidity: Int,
    val apparentTemp: Int,
    val precipitationProbability: Int,
    val weatherCode: Int,
    val windSpeed: Int,
    val windDirection: Int,
    val windGusts: Int,
)
