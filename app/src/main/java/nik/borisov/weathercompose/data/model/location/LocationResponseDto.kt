package nik.borisov.weathercompose.data.model.location

import com.google.gson.annotations.SerializedName

data class LocationResponseDto(

    @SerializedName("results")
    val responseResult: List<LocationDto>?
)
