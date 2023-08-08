package nik.borisov.weathercompose.presentation.screen.location

import nik.borisov.weathercompose.presentation.model.location.LocationItemUi

sealed class LocationScreenState {

    object Initial : LocationScreenState()

    data class Location(
        val locations: List<LocationItemUi>,
        val useCurrentLocation: Boolean,
        val autoCompleteLocation: List<LocationItemUi>,
        val error: String
    ) : LocationScreenState()
}
