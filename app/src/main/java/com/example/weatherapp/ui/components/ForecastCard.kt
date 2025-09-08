package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.model.DailyForecast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ForecastCard(f: DailyForecast) {
    Card(
        modifier = Modifier.padding(8.dp).width(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(formatDate(f.date), style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            val iconUrl = "https://openweathermap.org/img/wn/${f.icon}@2x.png"
            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = f.condition,
                modifier = Modifier.size(64.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text("${f.temperature}°C", style = MaterialTheme.typography.headlineSmall)
            Text(f.condition, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun formatDate(isoDate: String): String {
    return try {
        LocalDate.parse(isoDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .format(DateTimeFormatter.ofPattern("EEE, MMM d"))
    } catch (e: Exception) {
        isoDate
    }
}
