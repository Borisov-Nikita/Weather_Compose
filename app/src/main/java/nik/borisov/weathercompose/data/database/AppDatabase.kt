package nik.borisov.weathercompose.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.data.database.dao.ForecastDao
import nik.borisov.weathercompose.data.database.dao.LocationDao
import nik.borisov.weathercompose.data.database.dao.SettingsDao
import nik.borisov.weathercompose.data.model.forecast.ForecastDto
import nik.borisov.weathercompose.data.model.location.CurrentLocationDto
import nik.borisov.weathercompose.data.model.location.LocationDto
import nik.borisov.weathercompose.data.model.settings.AppSettingsDto

@Database(
    entities = [
        LocationDto::class,
        CurrentLocationDto::class,
        ForecastDto::class,
        AppSettingsDto::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao
    abstract fun getForecastDao(): ForecastDao
    abstract fun getSettingsDao(): SettingsDao

    companion object {
        private const val DB_NAME = "application.db"

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        private val PREPOPULATE_CURRENT_LOCATION_USE_STATE = CurrentLocationDto(
            isCurrentLocationUsed = false
        )

        private val PREPOPULATE_SETTINGS = AppSettingsDto()

        private val scope = CoroutineScope(Dispatchers.IO)

        fun getInstance(application: Application): AppDatabase =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
            }

        private fun buildDatabase(application: Application) =
            Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DB_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
                        getInstance(application).getLocationDao().addCurrentLocationUseState(
                            PREPOPULATE_CURRENT_LOCATION_USE_STATE
                        )
                        getInstance(application).getSettingsDao().addSettings(
                            PREPOPULATE_SETTINGS
                        )
                    }
                }
            }).build()
    }
}