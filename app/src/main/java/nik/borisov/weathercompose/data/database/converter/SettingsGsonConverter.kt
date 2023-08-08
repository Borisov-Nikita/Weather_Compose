package nik.borisov.weathercompose.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nik.borisov.weathercompose.data.model.settings.SpeedUnitsData
import nik.borisov.weathercompose.data.model.settings.TempUnitsData

class SettingsGsonConverter {

    @TypeConverter
    fun convertJsonToTempUnitsData(json: String): TempUnitsData {
        val type = object : TypeToken<TempUnitsData>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun convertTempUnitsDataToJson(unit: TempUnitsData): String {
        val type = object : TypeToken<TempUnitsData>() {}.type
        return Gson().toJson(unit, type)
    }

    @TypeConverter
    fun convertJsonToSpeedUnitsData(json: String): SpeedUnitsData {
        val type = object : TypeToken<SpeedUnitsData>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun convertSpeedUnitsDataToJson(unit: SpeedUnitsData): String {
        val type = object : TypeToken<SpeedUnitsData>() {}.type
        return Gson().toJson(unit, type)
    }
}