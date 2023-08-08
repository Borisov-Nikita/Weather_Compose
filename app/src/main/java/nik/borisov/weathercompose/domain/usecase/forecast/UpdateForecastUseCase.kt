package nik.borisov.weathercompose.domain.usecase.forecast

import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.ForecastRepository
import javax.inject.Inject

class UpdateForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend operator fun invoke(
        locations: List<LocationItem>,
        onForecastUpdated: (Boolean) -> Unit
    ) {
        repository.updateForecast(
            locations = locations,
            onForecastUpdated = onForecastUpdated
        )
    }
}