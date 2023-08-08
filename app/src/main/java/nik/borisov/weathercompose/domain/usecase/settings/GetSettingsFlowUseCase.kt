package nik.borisov.weathercompose.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsFlowUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<AppSettings> {
        return repository.getSettingsFlow()
    }
}