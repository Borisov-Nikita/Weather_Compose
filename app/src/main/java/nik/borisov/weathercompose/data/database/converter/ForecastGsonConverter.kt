package nik.borisov.weathercompose.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nik.borisov.weathercompose.data.model.forecast.ForecastResponseDto
import nik.borisov.weathercompose.data.model.forecast.ForecastUnitsDto

class ForecastGsonConverter {

    @TypeConverter
    fun convertJsonToForecastDto(json: String): ForecastResponseDto {
        val type = object : TypeToken<ForecastResponseDto>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun convertForecastDtoToJson(dto: ForecastResponseDto): String {
        val type = object : TypeToken<ForecastResponseDto>() {}.type
        return Gson().toJson(dto, type)
    }

    @TypeConverter
    fun convertJsonToForecastUnitsDto(json: String): ForecastUnitsDto {
        val type = object : TypeToken<ForecastUnitsDto>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun convertForecastUnitsDtoToJson(dto: ForecastUnitsDto): String {
        val type = object : TypeToken<ForecastUnitsDto>() {}.type
        return Gson().toJson(dto, type)
    }
}