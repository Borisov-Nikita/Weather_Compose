package nik.borisov.weathercompose.presentation.screen.base

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun <T> VerticalScrollableContentWithDismiss(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    items: List<T>,
    onDismissed: (T) -> Unit,
    content: @Composable (T) -> Unit = {}
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = items,
            key = {
                it.hashCode()
            }
        ) { item ->
            val dismissState = rememberDismissState(
                positionalThreshold = { density ->
                    density * 0.5f
                },
                confirmValueChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onDismissed(item)
                    }
                    true
                }
            )
            SwipeToDismiss(
                modifier = Modifier
                    .animateItemPlacement(),
                state = dismissState,
                background = {
                    if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                        Box(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                },
                directions = setOf(DismissDirection.EndToStart),
                dismissContent = {
                    content(item)
                }
            )
        }
    }
}