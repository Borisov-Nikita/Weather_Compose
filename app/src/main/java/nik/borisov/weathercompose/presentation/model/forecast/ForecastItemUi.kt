package nik.borisov.weathercompose.presentation.model.forecast

data class ForecastItemUi(

    val locationName: String,
    val units: ForecastUnitsItemUi,
    val currentWeather: CurrentWeatherItemUi,
    val hourlyForecasts: List<HourlyForecastItemUi>,
    val dailyForecasts: List<DailyForecastItemUi>
)
