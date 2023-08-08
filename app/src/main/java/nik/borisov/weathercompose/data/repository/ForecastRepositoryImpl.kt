package nik.borisov.weathercompose.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nik.borisov.weathercompose.data.database.dao.ForecastDao
import nik.borisov.weathercompose.data.database.dao.LocationDao
import nik.borisov.weathercompose.data.database.dao.SettingsDao
import nik.borisov.weathercompose.data.mapper.ForecastMapper
import nik.borisov.weathercompose.data.model.forecast.ForecastDto
import nik.borisov.weathercompose.data.model.settings.AppSettingsDto
import nik.borisov.weathercompose.data.network.NetworkResponse
import nik.borisov.weathercompose.data.network.service.ForecastApiService
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.repository.ForecastRepository
import nik.borisov.weathercompose.util.DataResult
import nik.borisov.weathercompose.util.DateTimeUtil
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val forecastDao: ForecastDao,
    private val settingsDao: SettingsDao,
    private val forecastApiService: ForecastApiService,
    private val forecastMapper: ForecastMapper,
    private val dateTimeUtil: DateTimeUtil
) : ForecastRepository, NetworkResponse {

    private val forecastsFlow = forecastDao.getForecasts()
        .map { forecastDtoList ->
            forecastDtoList.map { forecastDto ->
                forecastMapper.mapForecastDtoToEntity(
                    dto = forecastDto,
                    location = locationDao.getLocation(forecastDto.locationId),
                    hourStart = dateTimeUtil.getCurrentHourEpoch(),
                    dayStart = dateTimeUtil.getCurrentDayEpoch(),
                    hourAmount = 24
                )
            }
        }

    override fun getForecastFlow(): Flow<List<ForecastItem>> {
        return forecastsFlow
    }

    override suspend fun updateForecast(
        locations: List<LocationItem>,
        onForecastUpdated: (Boolean) -> Unit
    ) {
        downloadForecast(
            locations = locations,
            onForecastDownloaded = onForecastUpdated
        )
    }

    private suspend fun downloadForecast(
        locations: List<LocationItem>,
        onForecastDownloaded: (Boolean) -> Unit
    ) {
        val settings = settingsDao.getSettings() ?: AppSettingsDto()
        val resultList = mutableListOf<ForecastDto>()
        locations.forEach { location ->
            val networkResult = safeNetworkCall(
                call = {
                    forecastApiService.downloadForecast(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        tempUnit = settings.tempUnit.value,
                        speedUnit = settings.speedUnit.value
                    )
                },
                mapper = { forecastResponseDto ->
                    forecastMapper.mapForecastResponseToDto(
                        location = location,
                        settings = settings,
                        currentTimeEpoch = dateTimeUtil.getCurrentTimeEpoch(),
                        response = forecastResponseDto
                    )
                }
            )
            if (networkResult is DataResult.Success) {
                resultList.add(networkResult.data!!)
            } else {
                //TODO failure
            }
        }
        if (resultList.isNotEmpty()) {
            saveForecasts(resultList)
            onForecastDownloaded(true)
        } else {
            onForecastDownloaded(false)
        }
    }

    private suspend fun saveForecasts(
        forecasts: List<ForecastDto>
    ) {
        val checkedList = mutableListOf<ForecastDto>().apply {
            addAll(forecasts.filter { item ->
                locationDao.hasLocationById(item.locationId)
            })
        }
        if (checkedList.isNotEmpty()) {
            forecastDao.addForecastList(checkedList)
        }
    }
}