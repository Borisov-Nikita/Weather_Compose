package nik.borisov.weathercompose.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import nik.borisov.weathercompose.data.database.dao.LocationDao
import nik.borisov.weathercompose.data.mapper.LocationMapper
import nik.borisov.weathercompose.data.model.location.CurrentLocationDto
import nik.borisov.weathercompose.data.network.NetworkResponse
import nik.borisov.weathercompose.data.network.service.LocationApiService
import nik.borisov.weathercompose.domain.entity.location.CurrentLocationItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.LocationRepository
import nik.borisov.weathercompose.util.DataResult
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationApiService: LocationApiService,
    private val locationMapper: LocationMapper
) : LocationRepository, NetworkResponse {

    private val autoCompleteLocationFlow = MutableSharedFlow<DataResult<List<LocationItem>>>()

    private val hasLocationFlow = locationDao.hasLocation()

    private val currentLocationFlow =
        locationDao.getCurrentLocationUseState()
            .transform { currentLocationDto ->
                if (currentLocationDto != null) {
                    emit(locationMapper.mapCurrentLocationDtoToEntity(currentLocationDto))
                }
            }

    private val locationFlow = locationDao.getLocations()
        .map { locationDtoList ->
            locationDtoList.mapNotNull { item ->
                locationMapper.mapLocationDtoToEntity(item)
            }
        }

    override fun getLocationsFlow(): Flow<List<LocationItem>> {
        return locationFlow
    }

    override fun getHasLocationFlow(): Flow<Boolean> {
        return hasLocationFlow
    }

    override fun getCurrentLocationFlow(): Flow<CurrentLocationItem> {
        return currentLocationFlow
    }

    override fun getAutoCompleteLocationFlow(): SharedFlow<DataResult<List<LocationItem>>> {
        return autoCompleteLocationFlow.asSharedFlow()
    }

    override suspend fun addLocation(location: LocationItem) {
        locationDao.addLocation(locationMapper.mapLocationItemToDto(location))
    }

    override suspend fun deleteLocation(locationId: Int) {
        locationDao.deleteLocation(locationId)
    }

    override suspend fun useCurrentLocation(
        currentLocationItem: CurrentLocationItem,
        locationItem: LocationItem
    ) {
        locationDao.addCurrentLocationUseState(
            locationMapper.mapCurrentLocationItemToDto(currentLocationItem)
        )
        addLocation(locationItem)
    }

    override suspend fun doNotUseCurrentLocation() {
        locationDao.addCurrentLocationUseState(
            CurrentLocationDto(
                isCurrentLocationUsed = false
            )
        )
        deleteLocation(locationId = LocationItem.CURRENT_LOCATION_ID)
    }

    override suspend fun searchLocationAsAutoComplete(query: String) {
        val networkResult = if (query.length < 3) {
            DataResult.Success(listOf<LocationItem>())
        } else {
            safeNetworkCall(
                call = {
                    locationApiService.searchLocation(
                        query = query,


                        //TODO
                        language = "ru"
                    )
                },
                mapper = {
                    locationMapper.mapLocationResponseDtoToEntityList(it)
                }
            )
        }
        autoCompleteLocationFlow.emit(
            networkResult
        )
    }
}