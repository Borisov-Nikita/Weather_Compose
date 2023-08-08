package nik.borisov.weathercompose.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nik.borisov.weathercompose.domain.usecase.settings.GetSettingsFlowUseCase
import nik.borisov.weathercompose.domain.usecase.settings.SaveSettingsFlowUseCase
import nik.borisov.weathercompose.extension.mergeWith
import nik.borisov.weathercompose.presentation.mapper.UiSettingsMapper
import nik.borisov.weathercompose.presentation.model.settings.AppSettingsUi
import nik.borisov.weathercompose.presentation.model.settings.UnitsUi
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    getSettingsFlowUseCase: GetSettingsFlowUseCase,
    private val saveSettingsFlowUseCase: SaveSettingsFlowUseCase,
    private val settingsMapper: UiSettingsMapper
) : ViewModel() {

    private val settingsFlow = getSettingsFlowUseCase()

    private val settingsState = MutableStateFlow<AppSettingsUi?>(null)
    val screenState = settingsFlow
        .transform { settings ->
            val settingsUi = settingsMapper.mapAppSettingsToUi(settings)
            settingsState.update { settingsUi }
            emit(Unit)
        }.filterIsInstance<SettingsScreenState>()
        .mergeWith(
            settingsState
                .filterNotNull()
                .map { SettingsScreenState.Settings(it) }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SettingsScreenState.Initial
        )

    fun changeTempUnitsSettings(unit: UnitsUi) {
        settingsState.update { settings ->
            settings?.copy(
                temperatureUnit = settings.temperatureUnit.copy(choseValue = unit)
            )
        }
    }

    fun changeSpeedUnitsSettings(unit: UnitsUi) {
        settingsState.update { settings ->
            settings?.copy(
                speedUnit = settings.speedUnit.copy(choseValue = unit)
            )
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            saveSettingsFlowUseCase(
                settingsMapper.mapAppSettingsUiToEntity(
                    settingsState.value
                        ?: throw NullPointerException("Value of settingsState is null.")
                )
            )
        }
    }
}