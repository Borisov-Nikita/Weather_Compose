package nik.borisov.weathercompose.presentation.screen.settings

import nik.borisov.weathercompose.presentation.model.settings.AppSettingsUi

sealed class SettingsScreenState {

    object Initial : SettingsScreenState()

    data class Settings(
        val settings: AppSettingsUi
    ) : SettingsScreenState()
}
