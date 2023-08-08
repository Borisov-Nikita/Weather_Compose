package nik.borisov.weathercompose.data.network.service

import nik.borisov.weathercompose.data.model.forecast.ForecastResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {

    @GET("forecast?hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,weathercode,windspeed_10m,winddirection_10m,windgusts_10m,is_day&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_probability_max,windspeed_10m_max,windgusts_10m_max,winddirection_10m_dominant&timeformat=unixtime")
    suspend fun downloadForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("timezone") timezone: String = "GMT",
        @Query("temperature_unit") tempUnit: String,
        @Query("windspeed_unit") speedUnit: String
    ): Response<ForecastResponseDto>
}