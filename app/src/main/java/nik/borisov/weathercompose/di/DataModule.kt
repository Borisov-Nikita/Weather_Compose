package nik.borisov.weathercompose.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import nik.borisov.weathercompose.data.database.AppDatabase
import nik.borisov.weathercompose.data.database.dao.ForecastDao
import nik.borisov.weathercompose.data.database.dao.LocationDao
import nik.borisov.weathercompose.data.database.dao.SettingsDao
import nik.borisov.weathercompose.data.network.ApiFactory
import nik.borisov.weathercompose.data.network.service.ForecastApiService
import nik.borisov.weathercompose.data.network.service.LocationApiService
import nik.borisov.weathercompose.data.repository.ForecastRepositoryImpl
import nik.borisov.weathercompose.data.repository.LocationRepositoryImpl
import nik.borisov.weathercompose.data.repository.SettingsRepositoryImpl
import nik.borisov.weathercompose.domain.repository.ForecastRepository
import nik.borisov.weathercompose.domain.repository.LocationRepository
import nik.borisov.weathercompose.domain.repository.SettingsRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindForecastRepository(impl: ForecastRepositoryImpl): ForecastRepository

    @ApplicationScope
    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @ApplicationScope
    @Binds
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideForecastDao(application: Application): ForecastDao {
            return AppDatabase.getInstance(application).getForecastDao()
        }

        @ApplicationScope
        @Provides
        fun provideLocationDao(application: Application): LocationDao {
            return AppDatabase.getInstance(application).getLocationDao()
        }

        @ApplicationScope
        @Provides
        fun provideSettingsDao(application: Application): SettingsDao {
            return AppDatabase.getInstance(application).getSettingsDao()
        }

        @ApplicationScope
        @Provides
        fun provideForecastApiService(): ForecastApiService {
            return ApiFactory.forecastApiService
        }

        @ApplicationScope
        @Provides
        fun provideLocationApiService(): LocationApiService {
            return ApiFactory.locationApiService
        }
    }
}