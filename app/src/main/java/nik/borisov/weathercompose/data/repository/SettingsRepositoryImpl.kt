package nik.borisov.weathercompose.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import nik.borisov.weathercompose.data.database.dao.SettingsDao
import nik.borisov.weathercompose.data.mapper.SettingsMapper
import nik.borisov.weathercompose.domain.entity.settings.AppSettings
import nik.borisov.weathercompose.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao,
    private val settingsMapper: SettingsMapper
) : SettingsRepository {

    private val settingsDtoFlow = settingsDao.getSettingsFlow()

    override fun getSettingsFlow(): Flow<AppSettings> {
        return settingsDtoFlow.mapNotNull { dto ->
            if (dto != null) {
                settingsMapper.mapAppSettingsDtoToEntity(dto)
            } else {
                null
            }
        }
    }

    override suspend fun saveSettings(settings: AppSettings) {
        settingsDao.addSettings(
            settingsMapper.mapAppSettingsToDto(settings)
        )
    }
}