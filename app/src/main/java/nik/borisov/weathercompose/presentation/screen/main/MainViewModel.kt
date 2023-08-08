package nik.borisov.weathercompose.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nik.borisov.weathercompose.domain.usecase.location.GetCurrentLocationFlowUseCase
import nik.borisov.weathercompose.domain.usecase.location.GetHasLocationFlowUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getHasLocationFlowUseCase: GetHasLocationFlowUseCase,
    getCurrentLocationFlowUseCase: GetCurrentLocationFlowUseCase
) : ViewModel() {

    private val hasLocationFlow = getHasLocationFlowUseCase()

    private val currentLocationFlow = getCurrentLocationFlowUseCase()
        .map { currentLocationItem ->
            currentLocationItem.isCurrentLocationUsed
        }

    val screenState = hasLocationFlow
        .combine(currentLocationFlow) { hasLocation, useCurrentLocation ->
            handLocationFlowValue(hasLocation, useCurrentLocation)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainScreenState.Initial
        )

    private fun handLocationFlowValue(
        hasLocation: Boolean,
        useCurrentLocation: Boolean
    ): MainScreenState {
        return if (hasLocation || useCurrentLocation) {
            MainScreenState.Main
        } else {
            MainScreenState.SearchLocation
        }
    }
}