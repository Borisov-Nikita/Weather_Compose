package nik.borisov.weathercompose.data.model.forecast

import com.google.gson.annotations.SerializedName

data class DailyForecastsDto(

    @SerializedName("time")
    val time: List<Long>,
    @SerializedName("temperature_2m_max")
    val tempMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val tempMin: List<Double>,
    @SerializedName("sunrise")
    val sunrise: List<Long>,
    @SerializedName("sunset")
    val sunset: List<Long>,
    @SerializedName("precipitation_probability_max")
    val precipitationProbability: List<Int>,
    @SerializedName("weathercode")
    val weatherCode: List<Int>,
    @SerializedName("windspeed_10m_max")
    val windSpeed: List<Double>,
    @SerializedName("winddirection_10m_dominant")
    val windDirection: List<Int>,
    @SerializedName("windgusts_10m_max")
    val windGusts: List<Double>,
)
