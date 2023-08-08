package nik.borisov.weathercompose.domain.entity.forecast

import nik.borisov.weathercompose.domain.entity.settings.SpeedUnits
import nik.borisov.weathercompose.domain.entity.settings.TempUnits

data class ForecastUnitsItem(

    val tempUnit: TempUnits,
    val speedUnits: SpeedUnits
)
