package nik.borisov.weathercompose.domain.usecase.location

import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class GetHasLocationFlowUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.getHasLocationFlow()
    }
}