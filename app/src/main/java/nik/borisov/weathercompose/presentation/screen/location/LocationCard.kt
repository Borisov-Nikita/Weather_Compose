package nik.borisov.weathercompose.presentation.screen.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nik.borisov.weathercompose.presentation.model.location.LocationItemUi
import nik.borisov.weathercompose.presentation.screen.base.BaseContentCard

private val ContentPadding = 16.dp
private val CardMinHeight = 64.dp

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    location: LocationItemUi
) {
    BaseContentCard(
        modifier = modifier
            .heightIn(min = CardMinHeight)
    ) {
        Column(
            modifier = Modifier
                .padding(ContentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleLarge
            )
            if (location.regionAndCountry.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = location.regionAndCountry,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}