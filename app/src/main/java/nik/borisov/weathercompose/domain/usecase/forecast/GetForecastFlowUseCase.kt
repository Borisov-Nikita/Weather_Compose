package nik.borisov.weathercompose.domain.usecase.forecast

import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastFlowUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    operator fun invoke(): Flow<List<ForecastItem>> {
        return repository.getForecastFlow()
    }
}