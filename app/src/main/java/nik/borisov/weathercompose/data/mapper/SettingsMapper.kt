package nik.borisov.weathercompose.data.mapper

import nik.borisov.weathercompose.data.model.settings.AppSettingsDto
import nik.borisov.weathercompose.data.model.settings.SpeedUnitsData
import nik.borisov.weathercompose.data.model.settings.TempUnitsData
import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.entity.settings.SpeedUnits
import nik.borisov.weathercompose.domain.entity.settings.TempUnits
import javax.inject.Inject

class SettingsMapper @Inject constructor() {

    fun mapAppSettingsDtoToEntity(dto: AppSettingsDto): AppSettings {
        return AppSettings(
            temperatureUnit = getTempUnitsFromTempUnitsData(dto.tempUnit),
            speedUnit = getSpeedUnitsFromSpeedUnitsData(dto.speedUnit)
        )
    }

    fun mapAppSettingsToDto(entity: AppSettings): AppSettingsDto {
        return AppSettingsDto(
            tempUnit = getTempUnitDataFromTempUnits(entity.temperatureUnit),
            speedUnit = getSpeedUnitDataFromSpeedUnits(entity.speedUnit)
        )
    }

    private fun getTempUnitDataFromTempUnits(
        tempUnits: TempUnits
    ): TempUnitsData {
        return TempUnitsData.valueOf(tempUnits.name)
    }

    private fun getSpeedUnitDataFromSpeedUnits(
        speedUnits: SpeedUnits
    ): SpeedUnitsData {
        return SpeedUnitsData.valueOf(speedUnits.name)
    }

    private fun getTempUnitsFromTempUnitsData(
        tempUnitData: TempUnitsData
    ): TempUnits {
        return TempUnits.valueOf(tempUnitData.name)
    }

    private fun getSpeedUnitsFromSpeedUnitsData(
        speedUnitData: SpeedUnitsData
    ): SpeedUnits {
        return SpeedUnits.valueOf(speedUnitData.name)
    }
}