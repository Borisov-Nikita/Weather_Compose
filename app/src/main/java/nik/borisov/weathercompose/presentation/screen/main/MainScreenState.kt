package nik.borisov.weathercompose.presentation.screen.main

sealed class MainScreenState {

    object Initial : MainScreenState()

    object Main : MainScreenState()

    object SearchLocation : MainScreenState()
}
