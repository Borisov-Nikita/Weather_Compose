package nik.borisov.weathercompose.presentation.screen.forecast

import nik.borisov.weathercompose.presentation.model.forecast.ForecastItemUi

sealed class ForecastScreenState {

    object Initial : ForecastScreenState()

    object Loading : ForecastScreenState()

    data class Forecast(
        val forecast: List<ForecastItemUi>
    ) : ForecastScreenState()
}
