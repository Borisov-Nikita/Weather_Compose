package nik.borisov.weathercompose.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import nik.borisov.weathercompose.di.ApplicationComponent
import nik.borisov.weathercompose.di.DaggerApplicationComponent

class ForecastApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as ForecastApplication).component
}