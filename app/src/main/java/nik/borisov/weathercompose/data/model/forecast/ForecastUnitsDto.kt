package nik.borisov.weathercompose.data.model.forecast

import nik.borisov.weathercompose.data.model.settings.SpeedUnitsData
import nik.borisov.weathercompose.data.model.settings.TempUnitsData

data class ForecastUnitsDto(

    val tempUnit: TempUnitsData,
    val speedUnit: SpeedUnitsData
)
