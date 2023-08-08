package nik.borisov.weathercompose.domain.usecase.location

import nik.borisov.weathercompose.domain.entity.location.CurrentLocationItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class UseCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        currentLocationItem: CurrentLocationItem,
        locationItem: LocationItem
    ) {
        repository.useCurrentLocation(
            currentLocationItem = currentLocationItem,
            locationItem = locationItem
        )
    }
}