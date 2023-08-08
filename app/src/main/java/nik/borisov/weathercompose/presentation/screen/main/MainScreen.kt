package nik.borisov.weathercompose.presentation.screen.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.presentation.getApplicationComponent
import nik.borisov.weathercompose.presentation.navigation.AppNavGraph
import nik.borisov.weathercompose.presentation.navigation.Screen
import nik.borisov.weathercompose.presentation.navigation.rememberNavigationState
import nik.borisov.weathercompose.presentation.screen.forecast.ForecastScreen
import nik.borisov.weathercompose.presentation.screen.location.LocationScreen
import nik.borisov.weathercompose.presentation.screen.settings.SettingsScreen

@Composable
fun MainScreen(
    onFinishActivityListener: () -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    val navigationState = rememberNavigationState()

    val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentScreen = getCurrentScreen(backStackEntry?.destination?.route)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainTopAppBar(
                currentScreen = currentScreen,
                onBackPressed = {
                    when (screenState.value) {
                        is MainScreenState.SearchLocation -> {
                            onFinishActivityListener()
                        }

                        else -> {
                            navigationState.navHostController.navigateUp()
                        }
                    }
                },
                onSearchButtonClicked = { navigationState.navigateTo(Screen.Location.route) },
                onSettingsButtonClicked = { navigationState.navigateTo(Screen.Settings.route) },
                modifier = Modifier
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .height(
                        WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .fillMaxWidth()
            ) {}
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            startDestination = when (screenState.value) {
                is MainScreenState.SearchLocation -> {
                    Screen.Location.route
                }

                else -> {
                    Screen.Forecast.route
                }
            },
            forecastScreenContent = {
                ForecastScreen(
                    onErrorGot = { errorMessage ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = errorMessage,
                                withDismissAction = true,
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(paddingValues)
                )
            },
            locationScreenContent = {
                LocationScreen(
                    onErrorGot = { errorMessage ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = errorMessage,
                                withDismissAction = true,
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            },
            settingsScreenContent = {
                SettingsScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopAppBar(
    currentScreen: Screen,
    onBackPressed: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (currentScreen !is Screen.Forecast) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_description)
                    )
                }
            }
        },
        actions = {
            if (currentScreen is Screen.Forecast) {
                IconButton(onClick = { onSearchButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { onSettingsButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

private fun getCurrentScreen(route: String?): Screen {
    return when (route) {
        Screen.Location.route -> {
            Screen.Location
        }

        Screen.Settings.route -> {
            Screen.Settings
        }

        else -> {
            Screen.Forecast
        }
    }
}