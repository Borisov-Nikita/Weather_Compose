package nik.borisov.weathercompose.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.data.model.settings.AppSettingsDto

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSettings(settings: AppSettingsDto)

    @Query("SELECT * FROM settings WHERE id =1")
    fun getSettingsFlow(): Flow<AppSettingsDto?>

    @Query("SELECT * FROM settings WHERE id =1")
    suspend fun getSettings(): AppSettingsDto?
}