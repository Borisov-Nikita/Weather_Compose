package nik.borisov.weathercompose.presentation.model.forecast

import androidx.annotation.StringRes

data class ForecastUnitsItemUi(

    @StringRes val tempUnits: Int,
    @StringRes val speedUnits: Int,
    @StringRes val percentage: Int,
    @StringRes val tempUnitsFormat: Int,
    @StringRes val speedUnitsFormat: Int,
    @StringRes val percentageFormat: Int
)
