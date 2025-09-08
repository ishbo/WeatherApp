package com.example.weatherapp.data.local

import com.example.weatherapp.model.DailyForecast

fun DailyForecast.toEntity() = ForecastEntity(
    city = city,
    date = date,
    temperature = temperature,
    condition = condition,
    icon = icon
)

fun ForecastEntity.toDailyForecast() = DailyForecast(
    city = city,
    date = date,
    temperature = temperature,
    condition = condition,
    icon = icon
)
