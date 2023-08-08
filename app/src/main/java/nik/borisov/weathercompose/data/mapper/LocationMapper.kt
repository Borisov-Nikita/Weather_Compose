package nik.borisov.weathercompose.data.mapper

import nik.borisov.weathercompose.data.model.location.CurrentLocationDto
import nik.borisov.weathercompose.data.model.location.LocationDto
import nik.borisov.weathercompose.data.model.location.LocationResponseDto
import nik.borisov.weathercompose.domain.entity.location.CurrentLocationItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun mapLocationItemToDto(locationItem: LocationItem): LocationDto {
        return LocationDto(
            id = locationItem.id,
            name = locationItem.name,
            country = locationItem.country,
            region = locationItem.region,
            latitude = locationItem.latitude,
            longitude = locationItem.longitude,
            timeZoneId = locationItem.timeZoneId
        )
    }

    fun mapLocationDtoToEntity(locationDto: LocationDto): LocationItem? {
        return if (
            locationDto.id != null
            && locationDto.name != null
            && locationDto.latitude != null
            && locationDto.longitude != null
            && locationDto.timeZoneId != null
        ) {
            LocationItem(
                id = locationDto.id,
                name = locationDto.name,
                country = locationDto.country,
                region = locationDto.region,
                latitude = locationDto.latitude,
                longitude = locationDto.longitude,
                timeZoneId = locationDto.timeZoneId
            )
        } else {
            null
        }
    }

    fun mapLocationResponseDtoToEntityList(response: LocationResponseDto): List<LocationItem> {
        return response.responseResult
            ?.mapNotNull {
                mapLocationDtoToEntity(it)
            } ?: listOf()
    }

    fun mapCurrentLocationDtoToEntity(currentLocationDto: CurrentLocationDto): CurrentLocationItem {
        return CurrentLocationItem(
            isCurrentLocationUsed = currentLocationDto.isCurrentLocationUsed,
            lastUpdateEpoch = currentLocationDto.lastUpdateEpoch
        )
    }

    fun mapCurrentLocationItemToDto(currentLocationItem: CurrentLocationItem): CurrentLocationDto {
        return CurrentLocationDto(
            isCurrentLocationUsed = currentLocationItem.isCurrentLocationUsed,
            lastUpdateEpoch = currentLocationItem.lastUpdateEpoch
        )
    }
}