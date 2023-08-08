package nik.borisov.weathercompose.presentation.model.settings

import androidx.annotation.StringRes

data class UnitItemUi(

    @StringRes val categoryTitle: Int,
    val categoryValues: List<UnitsUi>,
    val choseValue: UnitsUi
)
