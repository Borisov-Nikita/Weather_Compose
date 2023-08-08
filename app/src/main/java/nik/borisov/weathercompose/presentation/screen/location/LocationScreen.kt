package nik.borisov.weathercompose.presentation.screen.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.presentation.getApplicationComponent
import nik.borisov.weathercompose.presentation.screen.base.AutoCompleteSearchBar
import nik.borisov.weathercompose.presentation.screen.base.BaseScreenStateWithUseServiceDisplay
import nik.borisov.weathercompose.presentation.screen.base.VerticalScrollableContentWithDismiss

private val ContentPadding = 8.dp

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    onErrorGot: (String) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: LocationViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val serviceState = viewModel.serviceState.collectAsStateWithLifecycle()

    when (val locationStateValue = screenState.value) {
        is LocationScreenState.Initial -> {}

        is LocationScreenState.Location -> {
            val listState = rememberLazyListState()
            var query by rememberSaveable { mutableStateOf("") }
            val expandedFab by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex == 0
                }
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(ContentPadding)
            ) {
                Column {
                    AutoCompleteSearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        query = query,
                        queryLabel = stringResource(R.string.search_location),
                        onQueryChanged = {
                            viewModel.searchAutoCompleteLocation(it)
                            query = it
                        },
                        autoComplete = locationStateValue.autoCompleteLocation,
                        onClearButtonClick = {
                            viewModel.searchAutoCompleteLocation("")
                            query = ""
                        },
                        onItemClick = {
                            viewModel.addLocation(it.id)
                        }
                    ) { locationItem ->
                        ListItem(
                            headlineContent = { Text(locationItem.name) },
                            supportingContent = { Text(locationItem.regionAndCountry) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }

                    VerticalScrollableContentWithDismiss(
                        modifier = Modifier,
                        listState = listState,
                        items = locationStateValue.locations,
                        onDismissed = { viewModel.deleteLocation(it.id) }
                    ) {
                        LocationCard(
                            location = it,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                if (!locationStateValue.useCurrentLocation) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            viewModel.useCurrentLocation()
                        },
                        expanded = expandedFab,
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(text = stringResource(id = R.string.current_location))
                        },
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.BottomEnd),
                    )
                }
            }

            if (locationStateValue.error.isNotBlank()) {
                onErrorGot(locationStateValue.error)
            }

            BaseScreenStateWithUseServiceDisplay(
                state = serviceState.value,
                onErrorGot = onErrorGot
            )
        }
    }
}