package nik.borisov.weathercompose.domain.usecase.location

import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class DoNotUseCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke() {
        repository.doNotUseCurrentLocation()
    }
}