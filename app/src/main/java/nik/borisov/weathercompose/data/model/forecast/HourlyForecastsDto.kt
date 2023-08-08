package nik.borisov.weathercompose.data.model.forecast

import com.google.gson.annotations.SerializedName

data class HourlyForecastsDto(

    @SerializedName("time")
    val time: List<Long>,
    @SerializedName("is_day")
    val isDay: List<Int>,
    @SerializedName("temperature_2m")
    val temp: List<Double>,
    @SerializedName("relativehumidity_2m")
    val humidity: List<Int>,
    @SerializedName("apparent_temperature")
    val apparentTemp: List<Double>,
    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("windspeed_10m")
    val windSpeed: List<Double>,
    @SerializedName("winddirection_10m")
    val windDirection: List<Int>,
    @SerializedName("windgusts_10m")
    val windGusts: List<Double>,
)
