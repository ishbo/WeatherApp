package com.example.weatherapp.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import com.example.weatherapp.model.DailyForecast

@Composable
fun ForecastList(forecasts: List<DailyForecast>) {
    LazyRow {
        items(forecasts.size) { index ->
            ForecastCard(forecasts[index])
        }
    }
}
