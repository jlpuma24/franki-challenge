package com.frankichallenge.presentation.screens.compose.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.frankichallenge.presentation.viewmodel.WeatherActions
import com.frankichallenge.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.state.collectAsState()

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            when (val state = weatherState) {
                is WeatherActions.Loading -> LoadingScreen()
                is WeatherActions.OnWeatherReceived -> WeatherScreen(state.weatherResponse)
                is WeatherActions.Error -> ErrorScreen { weatherViewModel.getWeather() }
                else -> {}
            }
        }
    }
}