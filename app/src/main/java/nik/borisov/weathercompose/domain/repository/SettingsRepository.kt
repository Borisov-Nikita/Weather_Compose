package nik.borisov.weathercompose.domain.repository

import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.domain.entity.settings.AppSettings

interface SettingsRepository {

    fun getSettingsFlow(): Flow<AppSettings>

    suspend fun saveSettings(settings: AppSettings)
}