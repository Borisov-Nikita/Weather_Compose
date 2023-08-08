package nik.borisov.weathercompose.domain.usecase.settings

import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsFlowUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(settings: AppSettings) {
        return repository.saveSettings(
            settings = settings
        )
    }
}