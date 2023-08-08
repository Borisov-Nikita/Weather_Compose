package nik.borisov.weathercompose.domain.usecase.location

import kotlinx.coroutines.flow.SharedFlow
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.LocationRepository
import nik.borisov.weathercompose.util.DataResult
import javax.inject.Inject

class GetAutoCompleteLocationFlowUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    operator fun invoke(): SharedFlow<DataResult<List<LocationItem>>> {
        return repository.getAutoCompleteLocationFlow()
    }
}