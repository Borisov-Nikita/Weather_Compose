package nik.borisov.weathercompose.presentation.screen.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nik.borisov.weathercompose.presentation.getApplicationComponent
import nik.borisov.weathercompose.presentation.model.settings.UnitItemUi
import nik.borisov.weathercompose.presentation.model.settings.UnitsUi
import nik.borisov.weathercompose.presentation.screen.base.BaseContentCard

private val ContentPadding = 8.dp

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val component = getApplicationComponent()
    val viewModel: SettingsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    DisposableEffect(key1 = viewModel) {
        onDispose {
            viewModel.saveSettings()
        }
    }

    var expandedItem by remember { mutableStateOf<Int?>(null) }

    when (val value = screenState.value) {
        is SettingsScreenState.Initial -> {}

        is SettingsScreenState.Settings -> {
            Column(
                modifier = modifier
                    .padding(ContentPadding)
            ) {

                AnimatedSettingsItem(
                    value = value.settings.temperatureUnit,
                    isExpanded = expandedItem == value.settings.temperatureUnit.categoryTitle,
                    onClick = {
                        expandedItem =
                            if (expandedItem == value.settings.temperatureUnit.categoryTitle) null
                            else value.settings.temperatureUnit.categoryTitle
                    },
                    unexpandedContent = {
                        SettingsItemUnitsTitle(item = it)
                    },
                    expandedContent = { item ->
                        UnitsSettingsChoice(
                            value = item,
                            onValueSelected = { unit ->
                                viewModel.changeTempUnitsSettings(unit)
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(ContentPadding))

                AnimatedSettingsItem(
                    value = value.settings.speedUnit,
                    isExpanded = expandedItem == value.settings.speedUnit.categoryTitle,
                    onClick = {
                        expandedItem =
                            if (expandedItem == value.settings.speedUnit.categoryTitle) null
                            else value.settings.speedUnit.categoryTitle
                    },
                    unexpandedContent = {
                        SettingsItemUnitsTitle(item = it)
                    },
                    expandedContent = { item ->
                        UnitsSettingsChoice(
                            value = item,
                            onValueSelected = { unit ->
                                viewModel.changeSpeedUnitsSettings(unit)
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsItemUnitsTitle(
    modifier: Modifier = Modifier,
    item: UnitItemUi
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = item.categoryTitle)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = item.choseValue.description),
            maxLines = 1
        )

    }
}

@Composable
private fun UnitsSettingsChoice(
    modifier: Modifier = Modifier,
    value: UnitItemUi,
    onValueSelected: (UnitsUi) -> Unit
) {
    SettingsItemValuesRadioGroup(
        onDescriptionSet = { item ->
            item.description
        },
        values = value.categoryValues,
        selectedValue = value.choseValue,
        onValueSelected = {
            onValueSelected(it)
        },
        modifier = modifier
    )
}

@Composable
private fun <T> SettingsItemValuesRadioGroup(
    modifier: Modifier = Modifier,
    onDescriptionSet: (T) -> Int,
    values: List<T>,
    selectedValue: T,
    onValueSelected: (T) -> Unit
) {
    Column(modifier.selectableGroup()) {
        values.forEach { tempUnit ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (tempUnit == selectedValue),
                        onClick = { onValueSelected(tempUnit) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = onDescriptionSet(tempUnit)))
                RadioButton(
                    selected = tempUnit == selectedValue,
                    onClick = null
                )
            }
        }
    }
}

@Composable
private fun <T> AnimatedSettingsItem(
    modifier: Modifier = Modifier,
    value: T,
    isExpanded: Boolean,
    onClick: (T) -> Unit,
    unexpandedContent: @Composable (T) -> Unit,
    expandedContent: @Composable (T) -> Unit
) {

    BaseContentCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick(value) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            unexpandedContent(value)

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                expandedContent(value)
            }
        }
    }
}