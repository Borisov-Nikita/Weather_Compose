package nik.borisov.weathercompose.presentation.screen.forecast

import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem

data class ForecastViewModelState(

    val forecast: List<ForecastItem> = listOf(),
    val location: List<LocationItem> = listOf(),
    val settings: AppSettings = AppSettings(),
    val isInitial: Boolean = true
)
