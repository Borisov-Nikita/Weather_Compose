package nik.borisov.weathercompose.presentation.screen.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nik.borisov.weathercompose.R
import nik.borisov.weathercompose.presentation.getApplicationComponent
import nik.borisov.weathercompose.presentation.model.forecast.CurrentWeatherItemUi
import nik.borisov.weathercompose.presentation.model.forecast.ForecastItemUi
import nik.borisov.weathercompose.presentation.model.forecast.ForecastUnitsItemUi
import nik.borisov.weathercompose.presentation.screen.base.BaseContentCard
import nik.borisov.weathercompose.presentation.screen.base.BaseScreenStateWithUseServiceDisplay

private val ContentPadding = 8.dp
private val CurrentWeatherConditionIconSize = 64.dp
private val CurrentWeatherConditionIconSmallSize = 32.dp
private val CurrentWeatherDescriptionIconSize = 28.dp

private enum class ForecastScreenContent {
    CURRENT,
    HOURLY,
    DAILY
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForecastScreen(
    modifier: Modifier = Modifier,
    onErrorGot: (String) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: ForecastViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val serviceState = viewModel.serviceState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
    ) {
        when (val value = screenState.value) {
            is ForecastScreenState.Initial -> {}

            is ForecastScreenState.Loading -> {
                LoadingContent(
                    modifier = Modifier
                        .padding(ContentPadding)
                )
            }

            is ForecastScreenState.Forecast -> {
                if (value.forecast.size > 1) {
                    val pagerState = rememberPagerState(
                        pageCount = { value.forecast.size }
                    )

                    Column(
                        modifier = Modifier
                            .padding(vertical = ContentPadding)
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .weight(1f)
                        ) { page ->
                            ForecastContent(
                                forecast = value.forecast[page],
                                modifier = Modifier
                                    .padding(horizontal = ContentPadding)
                            )
                        }
                        Spacer(modifier = Modifier.height(ContentPadding))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(value.forecast.size) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(10.dp)
                                )
                            }
                        }
                    }
                } else {
                    ForecastContent(
                        forecast = value.forecast[0],
                        modifier = Modifier
                            .padding(ContentPadding)
                    )
                }

                FloatingActionButton(
                    onClick = { viewModel.refreshForecast() },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
                )
            }
        }
        BaseScreenStateWithUseServiceDisplay(
            state = serviceState.value,
            onErrorGot = onErrorGot
        )
    }
}

@Composable
private fun ForecastContent(
    modifier: Modifier = Modifier,
    forecast: ForecastItemUi
) {
    var expanded by rememberSaveable { mutableStateOf(ForecastScreenContent.CURRENT) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AnimatedExpandedContent(
            modifier = if (expanded == ForecastScreenContent.CURRENT)
                Modifier.weight(1f)
            else
                Modifier,
            isExpanded = expanded == ForecastScreenContent.CURRENT,
            onItemClick = {
                if (expanded != ForecastScreenContent.CURRENT) {
                    expanded = ForecastScreenContent.CURRENT
                }
            },
            unexpandedContent = {
                UnexpandedContent(
                    additionalContent = {
                        CurrentWeatherShortContent(
                            locationName = forecast.locationName,
                            content = forecast.currentWeather,
                            units = forecast.units
                        )
                    }
                )
            },
            expandedContent = {
                CurrentWeatherContent(
                    locationName = forecast.locationName,
                    content = forecast.currentWeather,
                    units = forecast.units
                )
            }
        )

        Spacer(modifier = Modifier.height(ContentPadding))

        AnimatedExpandedContent(
            modifier = if (expanded == ForecastScreenContent.HOURLY)
                Modifier.weight(1f)
            else
                Modifier,
            isExpanded = expanded == ForecastScreenContent.HOURLY,
            onItemClick = {
                if (expanded != ForecastScreenContent.HOURLY) {
                    expanded = ForecastScreenContent.HOURLY
                }
            },
            unexpandedContent = { UnexpandedContent(title = R.string.hourly_forecast) },
            expandedContent = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(ContentPadding)
                ) {
                    itemsIndexed(
                        items = forecast.hourlyForecasts
                    ) { index, hourlyForecastItemUi ->
                        HourlyForecastCard(
                            hourlyForecast = hourlyForecastItemUi,
                            units = forecast.units,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = ContentPadding)
                        )
                        if (index < forecast.hourlyForecasts.size - 1) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(ContentPadding))
        AnimatedExpandedContent(
            modifier = if (expanded == ForecastScreenContent.DAILY)
                Modifier.weight(1f)
            else
                Modifier,
            isExpanded = expanded == ForecastScreenContent.DAILY,
            onItemClick = {
                if (expanded != ForecastScreenContent.DAILY) {
                    expanded = ForecastScreenContent.DAILY
                }
            },
            unexpandedContent = { UnexpandedContent(title = R.string.daily_forecast) },
            expandedContent = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(ContentPadding)
                ) {
                    itemsIndexed(
                        items = forecast.dailyForecasts
                    ) { index, dailyForecastItemUi ->
                        DailyForecastCard(
                            dailyForecast = dailyForecastItemUi,
                            units = forecast.units,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = ContentPadding)
                        )
                        if (index < forecast.dailyForecasts.size - 1) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun AnimatedExpandedContent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onItemClick: () -> Unit,
    unexpandedContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        if (isExpanded) {
            BaseContentCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                expandedContent()
            }
        } else {
            BaseContentCard(
                onClick = { onItemClick() },
                modifier = modifier
                    .fillMaxWidth()
            ) {
                unexpandedContent()
            }
        }
    }
}

@Composable
private fun CurrentWeatherContent(
    modifier: Modifier = Modifier,
    locationName: String,
    content: CurrentWeatherItemUi,
    units: ForecastUnitsItemUi,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .padding(ContentPadding)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = locationName,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(ContentPadding))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = content.conditionIcon),
                contentDescription = null,
                modifier = Modifier
                    .height(CurrentWeatherConditionIconSize)
            )
            Spacer(modifier = Modifier.width(ContentPadding))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = units.tempUnitsFormat, content.temp),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                )
            }
        }
        Spacer(modifier = Modifier.height(ContentPadding))
        Text(
            text = stringResource(
                id = R.string.temp_max_min_format,
                content.tempMax,
                content.tempMin,
                stringResource(
                    id = units.tempUnits
                )
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(ContentPadding))
        Text(
            text = stringResource(id = content.conditionDescription),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(ContentPadding))
        Text(
            text = stringResource(
                id = R.string.temp_feels_like_format,
                content.apparentTemp,
                stringResource(id = units.tempUnits)
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(ContentPadding))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CurrentWeatherAdditionalDescription(
                icon = R.drawable.ic_wind,
                value = content.windSpeed,
                description = R.string.wind,
                unitFormat = units.speedUnitsFormat,
                modifier = Modifier.weight(1f)
            )
            CurrentWeatherAdditionalDescription(
                icon = R.drawable.ic_precipitation,
                value = content.precipitationProbability,
                description = R.string.rainfall,
                unitFormat = units.percentageFormat,
                modifier = Modifier.weight(1f)
            )
            CurrentWeatherAdditionalDescription(
                icon = R.drawable.ic_humidity,
                value = content.humidity,
                description = R.string.humidity,
                unitFormat = units.percentageFormat,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CurrentWeatherAdditionalDescription(
    modifier: Modifier = Modifier,
    value: String,
    @DrawableRes icon: Int,
    @StringRes description: Int,
    @StringRes unitFormat: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(CurrentWeatherDescriptionIconSize)
        )
        Spacer(Modifier.height(ContentPadding))
        Row {
            Text(
                text = stringResource(id = unitFormat, value),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(ContentPadding))
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun UnexpandedContent(
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null,
    additionalContent: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ContentPadding * 2, vertical = ContentPadding)
    ) {
        if (title != null) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge
            )
        }
        additionalContent()
    }
}

@Composable
private fun CurrentWeatherShortContent(
    modifier: Modifier = Modifier,
    locationName: String,
    content: CurrentWeatherItemUi,
    units: ForecastUnitsItemUi,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = locationName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
        )
        Image(
            painter = painterResource(id = content.conditionIcon),
            contentDescription = null,
            modifier = Modifier
                .height(CurrentWeatherConditionIconSmallSize)
        )
        Spacer(modifier = Modifier.width(ContentPadding))
        Text(
            text = stringResource(id = units.tempUnitsFormat, content.temp),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes { durationMillis = 1500 },
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BaseContentCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(ContentPadding)
                    .fillMaxSize()
            ) {
                LoadingItem(height = 32.dp, alpha = alpha)
                Spacer(modifier = Modifier.height(ContentPadding))
                LoadingItem(height = 64.dp, alpha = alpha)
                Spacer(modifier = Modifier.height(ContentPadding))
                LoadingItem(height = 24.dp, alpha = alpha)
                Spacer(modifier = Modifier.height(ContentPadding))
                LoadingItem(height = 18.dp, alpha = alpha)
                Spacer(modifier = Modifier.height(ContentPadding))
                LoadingItem(height = 18.dp, alpha = alpha)
                Spacer(modifier = Modifier.height(ContentPadding))
                LoadingItem(height = 96.dp, alpha = alpha)
            }
        }
        repeat(2) {
            Spacer(modifier = Modifier.height(ContentPadding))
            BaseContentCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LoadingItem(
                    height = 32.dp,
                    alpha = alpha,
                    modifier = Modifier.padding(ContentPadding)
                )
            }
        }
    }
}

@Composable
private fun LoadingItem(
    modifier: Modifier = Modifier,
    height: Dp,
    alpha: Float = 1f
) {
    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .background(Color.LightGray.copy(alpha = alpha))
    )
}