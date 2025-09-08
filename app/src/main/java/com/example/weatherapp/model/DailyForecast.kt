package com.example.weatherapp.model

data class DailyForecast(
    val city: String,
    val date: String,
    val temperature: Double,
    val condition: String,
    val icon: String
)
