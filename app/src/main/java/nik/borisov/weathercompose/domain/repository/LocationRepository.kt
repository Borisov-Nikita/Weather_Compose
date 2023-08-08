package nik.borisov.weathercompose.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import nik.borisov.weathercompose.domain.entity.location.CurrentLocationItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.util.DataResult

interface LocationRepository {

    fun getLocationsFlow(): Flow<List<LocationItem>>

    fun getHasLocationFlow(): Flow<Boolean>

    fun getCurrentLocationFlow(): Flow<CurrentLocationItem>

    fun getAutoCompleteLocationFlow(): SharedFlow<DataResult<List<LocationItem>>>

    suspend fun addLocation(location: LocationItem)

    suspend fun deleteLocation(locationId: Int)

    suspend fun useCurrentLocation(
        currentLocationItem: CurrentLocationItem,
        locationItem: LocationItem
    )

    suspend fun doNotUseCurrentLocation()

    suspend fun searchLocationAsAutoComplete(query: String)
}