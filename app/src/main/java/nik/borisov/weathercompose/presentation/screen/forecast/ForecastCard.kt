package nik.borisov.weathercompose.presentation.screen.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.presentation.model.forecast.DailyForecastItemUi
import nik.borisov.weathercompose.presentation.model.forecast.ForecastUnitsItemUi
import nik.borisov.weathercompose.presentation.model.forecast.HourlyForecastItemUi

private val ContentPadding = 8.dp
private val ConditionIconSize = 32.dp
private val DescriptionIconSize = 16.dp

@Composable
fun DailyForecastCard(
    modifier: Modifier = Modifier,
    dailyForecast: DailyForecastItemUi,
    units: ForecastUnitsItemUi
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(ContentPadding)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = dailyForecast.dayOfWeek,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = dailyForecast.date,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.padding(ContentPadding))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = dailyForecast.conditionDescription),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(3f)
            ) {
                Image(
                    painter = painterResource(id = dailyForecast.conditionIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ConditionIconSize)
                )
                Spacer(modifier = Modifier.width(ContentPadding))
                Column() {
                    Text(
                        text = stringResource(id = units.tempUnitsFormat, dailyForecast.tempMax),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(ContentPadding))
                    Text(
                        text = stringResource(id = units.tempUnitsFormat, dailyForecast.tempMin),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                AdditionalDescription(
                    value = dailyForecast.windSpeed,
                    icon = R.drawable.ic_wind,
                    unitFormat = units.speedUnitsFormat
                )
                Spacer(modifier = Modifier.padding(ContentPadding))
                AdditionalDescription(
                    value = dailyForecast.precipitationProbability,
                    icon = R.drawable.ic_precipitation,
                    unitFormat = units.percentageFormat
                )
            }
        }
    }
}

@Composable
fun HourlyForecastCard(
    modifier: Modifier = Modifier,
    hourlyForecast: HourlyForecastItemUi,
    units: ForecastUnitsItemUi
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(ContentPadding)
            .fillMaxWidth()
    ) {
        Text(
            text = hourlyForecast.time,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = hourlyForecast.conditionDescription),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(3f)
            ) {
                Image(
                    painter = painterResource(id = hourlyForecast.conditionIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ConditionIconSize)
                )
                Spacer(modifier = Modifier.width(ContentPadding))
                Text(
                    text = stringResource(id = units.tempUnitsFormat, hourlyForecast.temp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                AdditionalDescription(
                    value = hourlyForecast.windSpeed,
                    icon = R.drawable.ic_wind,
                    unitFormat = units.speedUnitsFormat
                )
                Spacer(modifier = Modifier.padding(ContentPadding))
                AdditionalDescription(
                    value = hourlyForecast.precipitationProbability,
                    icon = R.drawable.ic_precipitation,
                    unitFormat = units.percentageFormat
                )
                Spacer(modifier = Modifier.padding(ContentPadding))
                AdditionalDescription(
                    value = hourlyForecast.humidity,
                    icon = R.drawable.ic_humidity,
                    unitFormat = units.percentageFormat
                )
            }
        }
    }
}

@Composable
private fun AdditionalDescription(
    modifier: Modifier = Modifier,
    value: String,
    @DrawableRes icon: Int,
    @StringRes unitFormat: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(DescriptionIconSize)
        )
        Spacer(modifier = Modifier.width(ContentPadding))
        Text(
            text = stringResource(id = unitFormat, value),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}