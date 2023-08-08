package nik.borisov.weathercompose.data.network

import nik.borisov.weathercompose.data.network.service.ForecastApiService
import nik.borisov.weathercompose.data.network.service.LocationApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val LOCATION_BASE_URL = "https://geocoding-api.open-meteo.com/v1/"
    private const val FORECAST_BASE_URL = "https://api.open-meteo.com/v1/"

    private val locationRetrofit = Retrofit.Builder()
        .baseUrl(LOCATION_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    private val forecastRetrofit = Retrofit.Builder()
        .baseUrl(FORECAST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((createOkHttpClient()))
        .build()

    val locationApiService: LocationApiService =
        locationRetrofit.create(LocationApiService::class.java)
    val forecastApiService: ForecastApiService =
        forecastRetrofit.create(ForecastApiService::class.java)

    private fun createHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun createOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(createHttpLoggingInterceptor())
        .build()

}