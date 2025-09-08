package com.example.weatherapp

import WeatherRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.network.NetworkModule
import com.example.weatherapp.ui.WeatherApp
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        val repo = WeatherRepository(NetworkModule.api, db.forecastDao(), applicationContext)
        val factory = WeatherViewModelFactory(repo)
        val vm = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        setContent {
            WeatherApp(vm)
        }
    }
}
