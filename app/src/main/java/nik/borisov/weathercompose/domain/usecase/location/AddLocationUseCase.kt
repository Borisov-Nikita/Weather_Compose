package nik.borisov.weathercompose.domain.usecase.location

import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(location: LocationItem) {
        repository.addLocation(
            location = location
        )
    }
}