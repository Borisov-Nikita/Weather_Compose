package nik.borisov.weathercompose.domain.repository

import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem

interface ForecastRepository {

    fun getForecastFlow(): Flow<List<ForecastItem>>

    suspend fun updateForecast(
        locations: List<LocationItem>,
        onForecastUpdated: (Boolean) -> Unit
    )
}