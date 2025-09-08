package com.example.weatherapp.ui

import WeatherRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.DailyForecast
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle: UiState()
    object Loading: UiState()
    data class Success(val forecasts: List<DailyForecast>): UiState()
    data class Error(val message: String): UiState()
}

class WeatherViewModel(private val repo: WeatherRepository): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetch(city: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val res = repo.get3DayForecastForCity(city.trim())
            if (res.isSuccess) {
                _uiState.value = UiState.Success(res.getOrDefault(emptyList()))
            } else {
                _uiState.value = UiState.Error(res.exceptionOrNull()?.localizedMessage ?: "Error")
            }
        }
    }
}
