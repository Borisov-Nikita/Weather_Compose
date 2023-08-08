package nik.borisov.weathercompose.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nik.borisov.weathercompose.presentation.screen.ViewModelFactory

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}