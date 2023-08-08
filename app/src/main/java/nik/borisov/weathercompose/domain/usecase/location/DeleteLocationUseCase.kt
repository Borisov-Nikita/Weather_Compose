package nik.borisov.weathercompose.domain.usecase.location

import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(locationId: Int) {
        repository.deleteLocation(
            locationId = locationId
        )
    }
}