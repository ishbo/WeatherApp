package com.example.weatherapp.util

import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.ForecastResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ForecastAggregator {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun aggregate(response: ForecastResponse): List<DailyForecast> {
        val byDate = response.list.groupBy {
            Instant.ofEpochSecond(it.dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(dateFormatter)
        }

        val dates = byDate.keys.sorted().take(3)

        return dates.map { date ->
            val items = byDate[date]!!

            val avgTemp = items.map { it.main.temp }.average()
            val condition = items.flatMap { it.weather }
                .groupingBy { it.description }
                .eachCount()
                .maxByOrNull { it.value }?.key ?: "Clear"
            val icon = items.flatMap { it.weather }
                .groupingBy { it.icon }
                .eachCount()
                .maxByOrNull { it.value }?.key ?: "01d"

            DailyForecast(
                city = response.city.name,
                date = date,
                temperature = avgTemp,
                condition = condition,
                icon = icon
            )
        }
    }
}
