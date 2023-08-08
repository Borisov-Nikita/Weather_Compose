package nik.borisov.weathercompose.data.network.service

import nik.borisov.weathercompose.data.model.location.LocationResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {

    @GET("search?")
    suspend fun searchLocation(
        @Query("name") query: String,
        @Query("count") countResult: Int = 10,
        @Query("language") language: String = "en"
    ): Response<LocationResponseDto>
}