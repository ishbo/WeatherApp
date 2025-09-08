package com.example.weatherapp.model

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
)

data class Main(val temp: Double, val temp_min: Double, val temp_max: Double)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)

data class City(val id: Long, val name: String, val country: String)
