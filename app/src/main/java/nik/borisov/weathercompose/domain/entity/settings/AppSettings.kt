package nik.borisov.weathercompose.domain.entity.settings

data class AppSettings(
    var temperatureUnit: TempUnits = TempUnits.CELSIUS,
    var speedUnit: SpeedUnits = SpeedUnits.KILOMETER
)