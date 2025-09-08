package com.example.weatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.ui.components.ForecastList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(vm: WeatherViewModel) {
    val uiState by vm.uiState.collectAsState()
    var city by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("3-Day Forecast") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row {
                TextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = { if (city.isNotBlank()) vm.fetch(city) }) {
                    Text("Fetch")
                }
            }
            Spacer(Modifier.height(16.dp))

            when (uiState) {
                is UiState.Idle -> Text("Enter a city and tap Fetch")
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text(
                    text = (uiState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
                is UiState.Success -> ForecastList((uiState as UiState.Success).forecasts)
            }
        }
    }
}
