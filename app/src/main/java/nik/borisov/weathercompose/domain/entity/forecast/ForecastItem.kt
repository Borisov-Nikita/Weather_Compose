package nik.borisov.weathercompose.domain.entity.forecast

import nik.borisov.weathercompose.domain.entity.location.LocationItem

data class ForecastItem(

    val lastUpdateEpoch: Long,
    val location: LocationItem,
    val units: ForecastUnitsItem,
    val hourlyForecasts: List<HourlyForecastItem>,
    val dailyForecasts: List<DailyForecastItem>
)
