package nik.borisov.weathercompose.data.mapper

import nik.borisov.weathercompose.data.model.forecast.ForecastDto
import nik.borisov.weathercompose.data.model.forecast.ForecastResponseDto
import nik.borisov.weathercompose.data.model.forecast.ForecastUnitsDto
import nik.borisov.weathercompose.data.model.location.LocationDto
import nik.borisov.weathercompose.data.model.settings.AppSettingsDto
import nik.borisov.weathercompose.domain.entity.forecast.DailyForecastItem
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.entity.forecast.ForecastUnitsItem
import nik.borisov.weathercompose.domain.entity.forecast.HourlyForecastItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.entity.settings.SpeedUnits
import nik.borisov.weathercompose.domain.entity.settings.TempUnits
import javax.inject.Inject
import kotlin.math.roundToInt

class ForecastMapper @Inject constructor() {

    private val locationMapper = LocationMapper()

    fun mapForecastResponseToDto(
        location: LocationItem,
        settings: AppSettingsDto,
        currentTimeEpoch: Long,
        response: ForecastResponseDto,
    ): ForecastDto {
        return ForecastDto(
            locationId = location.id,
            lastUpdateEpoch = currentTimeEpoch,
            forecast = response,
            units = mapSettingsToUnits(settings)
        )
    }

    fun mapForecastDtoToEntity(
        location: LocationDto,
        dto: ForecastDto,
        hourStart: Long,
        dayStart: Long,
        hourAmount: Int
    ): ForecastItem {
        return ForecastItem(
            lastUpdateEpoch = dto.lastUpdateEpoch,
            location = locationMapper.mapLocationDtoToEntity(location)
                ?: throw NullPointerException("LocationItem is null"),
            units = mapForecastUnitsDtoToEntity(dto.units),
            hourlyForecasts = mapForecastDtoToHourlyForecastItems(
                dto = dto.forecast,
                hourStart = hourStart,
                hourAmount = hourAmount
            ),
            dailyForecasts = mapForecastDtoToDailyForecastItem(
                dto = dto.forecast,
                dayStart = dayStart
            )
        )
    }

    private fun mapForecastDtoToHourlyForecastItems(
        dto: ForecastResponseDto,
        hourStart: Long,
        hourAmount: Int
    ): List<HourlyForecastItem> {
        val startHourIndex = dto.hourlyForecasts.time.indexOf(hourStart)
        val forecastList = mutableListOf<HourlyForecastItem>()
        if (startHourIndex < 0) return forecastList.toList()
        for (index in startHourIndex until startHourIndex + hourAmount) {
            forecastList.add(
                HourlyForecastItem(
                    time = dto.hourlyForecasts.time[index],
                    isDay = dto.hourlyForecasts.isDay[index] == 1,
                    temp = dto.hourlyForecasts.temp[index].roundToInt(),
                    humidity = dto.hourlyForecasts.humidity[index],
                    apparentTemp = dto.hourlyForecasts.apparentTemp[index].roundToInt(),
                    precipitationProbability = dto.hourlyForecasts.precipitationProbability[index],
                    weatherCode = dto.hourlyForecasts.weatherCode[index],
                    windSpeed = dto.hourlyForecasts.windSpeed[index].roundToInt(),
                    windDirection = dto.hourlyForecasts.windDirection[index],
                    windGusts = dto.hourlyForecasts.windGusts[index].roundToInt()
                )
            )
        }
        return forecastList.toList()
    }

    private fun mapForecastDtoToDailyForecastItem(
        dto: ForecastResponseDto,
        dayStart: Long
    ): List<DailyForecastItem> {
        val startDayIndex = dto.dailyForecasts.time.indexOf(dayStart)
        val forecastList = mutableListOf<DailyForecastItem>()
        if (startDayIndex < 0) return forecastList.toList()
        for (index in startDayIndex until dto.dailyForecasts.time.size) {
            forecastList.add(
                DailyForecastItem(
                    time = dto.dailyForecasts.time[index],
                    tempMax = dto.dailyForecasts.tempMax[index].roundToInt(),
                    tempMin = dto.dailyForecasts.tempMin[index].roundToInt(),
                    sunrise = dto.dailyForecasts.sunrise[index],
                    sunset = dto.dailyForecasts.sunset[index],
                    precipitationProbability = dto.dailyForecasts.precipitationProbability[index],
                    weatherCode = dto.dailyForecasts.weatherCode[index],
                    windSpeed = dto.dailyForecasts.windSpeed[index].roundToInt(),
                    windDirection = dto.dailyForecasts.windDirection[index],
                    windGusts = dto.dailyForecasts.windGusts[index].roundToInt()
                )
            )
        }
        return forecastList.toList()
    }

    private fun mapSettingsToUnits(settings: AppSettingsDto): ForecastUnitsDto {
        return ForecastUnitsDto(
            tempUnit = settings.tempUnit,
            speedUnit = settings.speedUnit
        )
    }

    private fun mapForecastUnitsDtoToEntity(dto: ForecastUnitsDto): ForecastUnitsItem {
        return ForecastUnitsItem(
            tempUnit = TempUnits.valueOf(dto.tempUnit.name),
            speedUnits = SpeedUnits.valueOf(dto.speedUnit.name)
        )
    }
}