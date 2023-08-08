package nik.borisov.weathercompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    forecastScreenContent: @Composable () -> Unit,
    locationScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.Forecast.route
        ) {
            forecastScreenContent()
        }
        composable(
            route = Screen.Location.route
        ) {
            locationScreenContent()
        }
        composable(
            route = Screen.Settings.route
        ) {
            settingsScreenContent()
        }
    }
}