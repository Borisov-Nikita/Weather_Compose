package nik.borisov.weathercompose.presentation.mapper

import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.domain.entity.forecast.DailyForecastItem
import nik.borisov.weathercompose.domain.entity.forecast.ForecastItem
import nik.borisov.weathercompose.domain.entity.forecast.ForecastUnitsItem
import nik.borisov.weathercompose.domain.entity.forecast.HourlyForecastItem
import nik.borisov.weathercompose.domain.entity.location.LocationItem
import nik.borisov.weathercompose.domain.entity.settings.SpeedUnits
import nik.borisov.weathercompose.domain.entity.settings.TempUnits
import nik.borisov.weathercompose.presentation.model.forecast.CurrentWeatherItemUi
import nik.borisov.weathercompose.presentation.model.forecast.DailyForecastItemUi
import nik.borisov.weathercompose.presentation.model.forecast.ForecastItemUi
import nik.borisov.weathercompose.presentation.model.forecast.ForecastUnitsItemUi
import nik.borisov.weathercompose.presentation.model.forecast.HourlyForecastItemUi
import nik.borisov.weathercompose.presentation.model.forecast.WeatherInterpretationCodes
import nik.borisov.weathercompose.presentation.model.location.LocationItemUi
import nik.borisov.weathercompose.util.DateTimeUtil
import javax.inject.Inject

class UiMapper @Inject constructor() {

    private val dateTimeUtil = DateTimeUtil()

    fun mapLocationItemToUi(locationItem: LocationItem): LocationItemUi {
        return LocationItemUi(
            id = locationItem.id,
            name = locationItem.name,
            regionAndCountry = buildString {
                if (locationItem.region != null) {
                    append(
                        locationItem.region
                    )
                }
                if (locationItem.region != null && locationItem.country != null) {
                    append(
                        ", "
                    )
                }
                if (locationItem.country != null) {
                    append(
                        locationItem.country
                    )
                }
            }
        )
    }

    fun mapForecastItemToUi(forecastItem: ForecastItem): ForecastItemUi {
        return ForecastItemUi(
            locationName = forecastItem.location.name,
            units = mapForecastUnitsItemToUi(forecastItem.units),
            currentWeather = mapForecastItemToCurrentWeatherUi(forecastItem),
            hourlyForecasts = forecastItem.hourlyForecasts.map { hourlyForecastItem ->
                mapHourlyForecastItemToUi(
                    hourlyForecastItem = hourlyForecastItem,
                    timeZone = forecastItem.location.timeZoneId
                )
            },
            dailyForecasts = forecastItem.dailyForecasts.map { dailyForecastItem ->
                mapDailyForecastItemToUi(
                    dailyForecastItem = dailyForecastItem,
                    timeZone = forecastItem.location.timeZoneId
                )
            }
        )
    }

    private fun mapForecastItemToCurrentWeatherUi(
        forecastItem: ForecastItem
    ): CurrentWeatherItemUi {
        return CurrentWeatherItemUi(
            time = dateTimeUtil.convertTimeDateFromEpochToString(
                time = forecastItem.hourlyForecasts[0].time,
                timeZoneId = forecastItem.location.timeZoneId,
                pattern = "E (dd MMM) HH:mm"
            ),
            sunrise = dateTimeUtil.convertTimeDateFromEpochToString(
                time = forecastItem.dailyForecasts[0].sunrise,
                timeZoneId = forecastItem.location.timeZoneId,
                pattern = "HH:mm"
            ),
            sunset = dateTimeUtil.convertTimeDateFromEpochToString(
                time = forecastItem.dailyForecasts[0].sunset,
                timeZoneId = forecastItem.location.timeZoneId,
                pattern = "HH:mm"
            ),
            conditionDescription = getConditionDescriptionFromCode(
                forecastItem.hourlyForecasts[0].weatherCode,
                forecastItem.hourlyForecasts[0].isDay
            ),
            conditionIcon = getConditionIconFromCode(
                forecastItem.hourlyForecasts[0].weatherCode,
                forecastItem.hourlyForecasts[0].isDay
            ),
            temp = forecastItem.hourlyForecasts[0].temp.toString(),
            apparentTemp = forecastItem.hourlyForecasts[0].apparentTemp.toString(),
            tempMax = forecastItem.dailyForecasts[0].tempMax.toString(),
            tempMin = forecastItem.dailyForecasts[0].tempMin.toString(),
            humidity = forecastItem.hourlyForecasts[0].humidity.toString(),
            precipitationProbability = forecastItem.hourlyForecasts[0].precipitationProbability.toString(),
            windSpeed = forecastItem.hourlyForecasts[0].windSpeed.toString(),
            windDirection = forecastItem.hourlyForecasts[0].windDirection.toString(),
            windGusts = forecastItem.hourlyForecasts[0].windGusts.toString(),
        )
    }

    private fun mapHourlyForecastItemToUi(
        hourlyForecastItem: HourlyForecastItem,
        timeZone: String
    ): HourlyForecastItemUi {
        return HourlyForecastItemUi(
            time = dateTimeUtil.convertTimeDateFromEpochToString(
                time = hourlyForecastItem.time,
                timeZoneId = timeZone,
                pattern = "HH:mm"
            ),
            conditionDescription = getConditionDescriptionFromCode(
                hourlyForecastItem.weatherCode,
                hourlyForecastItem.isDay
            ),
            conditionIcon = getConditionIconFromCode(
                hourlyForecastItem.weatherCode,
                hourlyForecastItem.isDay
            ),
            temp = hourlyForecastItem.temp.toString(),
            humidity = hourlyForecastItem.humidity.toString(),
            apparentTemp = hourlyForecastItem.apparentTemp.toString(),
            precipitationProbability = hourlyForecastItem.precipitationProbability.toString(),
            windSpeed = hourlyForecastItem.windSpeed.toString(),
            windDirection = hourlyForecastItem.windDirection.toString(),
            windGusts = hourlyForecastItem.windGusts.toString(),
        )
    }

    private fun mapDailyForecastItemToUi(
        dailyForecastItem: DailyForecastItem,
        timeZone: String
    ): DailyForecastItemUi {
        return DailyForecastItemUi(
            dayOfWeek = dateTimeUtil.convertTimeDateFromEpochToString(
                time = dailyForecastItem.time,
                timeZoneId = timeZone,
                pattern = "EEEE"
            ),
            date = dateTimeUtil.convertTimeDateFromEpochToString(
                time = dailyForecastItem.time,
                timeZoneId = timeZone,
                pattern = "d MMMM"
            ),
            conditionDescription = getConditionDescriptionFromCode(
                dailyForecastItem.weatherCode,
                true
            ),
            conditionIcon = getConditionIconFromCode(
                dailyForecastItem.weatherCode,
                true
            ),
            tempMax = dailyForecastItem.tempMax.toString(),
            tempMin = dailyForecastItem.tempMin.toString(),
            sunrise = dateTimeUtil.convertTimeDateFromEpochToString(
                time = dailyForecastItem.sunrise,
                timeZoneId = timeZone,
                pattern = "HH:mm"
            ),
            sunset = dateTimeUtil.convertTimeDateFromEpochToString(
                time = dailyForecastItem.sunset,
                timeZoneId = timeZone,
                pattern = "HH:mm"
            ),
            precipitationProbability = dailyForecastItem.precipitationProbability.toString(),
            windSpeed = dailyForecastItem.windSpeed.toString(),
            windDirection = dailyForecastItem.windDirection.toString(),
            windGusts = dailyForecastItem.windGusts.toString(),
        )
    }

    private fun mapForecastUnitsItemToUi(item: ForecastUnitsItem): ForecastUnitsItemUi {
        return ForecastUnitsItemUi(
            tempUnits = when (item.tempUnit) {
                TempUnits.CELSIUS -> {
                    R.string.unit_celsius
                }

                TempUnits.FAHRENHEIT -> {
                    R.string.unit_fahrenheit
                }
            },
            speedUnits = when (item.speedUnits) {
                SpeedUnits.KILOMETER -> {
                    R.string.unit_km_p_h
                }

                SpeedUnits.MILES -> {
                    R.string.unit_m_p_h
                }

                SpeedUnits.METERS -> {
                    R.string.unit_m_p_s
                }
            },
            percentage = R.string.unit_percentage,
            tempUnitsFormat = when (item.tempUnit) {
                TempUnits.CELSIUS -> {
                    R.string.unit_celsius_format
                }

                TempUnits.FAHRENHEIT -> {
                    R.string.unit_fahrenheit_format
                }
            },
            speedUnitsFormat = when (item.speedUnits) {
                SpeedUnits.KILOMETER -> {
                    R.string.unit_km_p_h_format
                }

                SpeedUnits.MILES -> {
                    R.string.unit_m_p_h_format
                }

                SpeedUnits.METERS -> {
                    R.string.unit_m_p_s_format
                }
            },
            percentageFormat = R.string.unit_percentage_format,
        )
    }

    private fun getConditionDescriptionFromCode(code: Int, isDay: Boolean): Int {
        return WeatherInterpretationCodes.values().first { item ->
            item.code == code
        }.getDescriptionResId(isDay)
    }

    private fun getConditionIconFromCode(code: Int, isDay: Boolean): Int {
        return WeatherInterpretationCodes.values().first { item ->
            item.code == code
        }.getIconResId(isDay)
    }
}