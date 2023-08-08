package nik.borisov.weathercompose.presentation.mapper

import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.entity.settings.SpeedUnits
import nik.borisov.weathercompose.domain.entity.settings.TempUnits
import nik.borisov.weathercompose.presentation.model.settings.AppSettingsUi
import nik.borisov.weathercompose.presentation.model.settings.UnitItemUi
import nik.borisov.weathercompose.presentation.model.settings.UnitsUi
import javax.inject.Inject

class UiSettingsMapper @Inject constructor() {

    fun mapAppSettingsToUi(settings: AppSettings): AppSettingsUi {
        return AppSettingsUi(
            temperatureUnit = getUnitsCategoryItemUi(settings.temperatureUnit),
            speedUnit = getUnitsCategoryItemUi(settings.speedUnit)
        )
    }

    fun mapAppSettingsUiToEntity(settingsUi: AppSettingsUi): AppSettings {
        return AppSettings(
            temperatureUnit = getTempUnit(settingsUi.temperatureUnit.choseValue),
            speedUnit = getSpeedUnit(settingsUi.speedUnit.choseValue)
        )
    }

    private fun getUnitsCategoryItemUi(
        tempUnits: TempUnits
    ): UnitItemUi {
        return UnitItemUi(
            categoryTitle = R.string.temperature,
            categoryValues = listOf(UnitsUi.CELSIUS, UnitsUi.FAHRENHEIT),
            choseValue = UnitsUi.valueOf(tempUnits.name),
        )
    }

    private fun getUnitsCategoryItemUi(
        speedUnits: SpeedUnits
    ): UnitItemUi {
        return UnitItemUi(
            categoryTitle = R.string.wind_speed,
            categoryValues = listOf(UnitsUi.KILOMETER, UnitsUi.MILES, UnitsUi.METERS),
            choseValue = UnitsUi.valueOf(speedUnits.name),
        )
    }

    private fun getTempUnit(unit: UnitsUi): TempUnits {
        return TempUnits.valueOf(unit.name)
    }

    private fun getSpeedUnit(unit: UnitsUi): SpeedUnits {
        return SpeedUnits.valueOf(unit.name)
    }
}