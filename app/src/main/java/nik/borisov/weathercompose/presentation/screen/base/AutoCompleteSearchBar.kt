package nik.borisov.weathercompose.presentation.screen.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutoCompleteSearchBar(
    modifier: Modifier,
    query: String,
    queryLabel: String,
    onQueryChanged: (String) -> Unit = {},
    autoComplete: List<T>,
    onDoneActionClick: (T) -> Unit = {},
    onClearButtonClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {}
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChanged,
        onSearch = {
            onQueryChanged(it.trim())
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = queryLabel) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = { onClearButtonClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    ) {
        LazyColumn(
            state = lazyListState,
        ) {
            if (autoComplete.isNotEmpty()) {
                items(
                    autoComplete
                ) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                active = false
                                onItemClick(item)
                            }
                    ) {
                        itemContent(item)
                    }
                }
            }
        }
    }
}