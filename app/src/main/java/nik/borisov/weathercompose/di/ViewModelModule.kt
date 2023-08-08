package nik.borisov.weathercompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nik.borisov.weathercompose.presentation.screen.forecast.ForecastViewModel
import nik.borisov.weathercompose.presentation.screen.location.LocationViewModel
import nik.borisov.weathercompose.presentation.screen.main.MainViewModel
import nik.borisov.weathercompose.presentation.screen.settings.SettingsViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    @Binds
    fun bindForecastViewModel(viewModel: ForecastViewModel): ViewModel

    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    @Binds
    fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    @Binds
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}