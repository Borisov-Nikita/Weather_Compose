package nik.borisov.weathercompose.data.model.forecast

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(

    @SerializedName("hourly")
    val hourlyForecasts: HourlyForecastsDto,
    @SerializedName("daily")
    val dailyForecasts: DailyForecastsDto
)
