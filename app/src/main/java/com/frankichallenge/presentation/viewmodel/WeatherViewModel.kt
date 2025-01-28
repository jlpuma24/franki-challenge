package com.frankichallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.domain.usecase.GetWeatherUseCase
import com.frankichallenge.domain.usecase.WeatherRequestGenericException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherActions>(WeatherActions.Idle)
    val state: StateFlow<WeatherActions> = _state

    fun getWeather() {
        viewModelScope.launch {
            try {
                _state.value = WeatherActions.Loading
                val result = getWeatherUseCase.execute()
                if (result.isSuccess) {
                    val weather = result.getOrNull()
                    if (weather != null) {
                        _state.value = WeatherActions.OnWeatherReceived(weather)
                    } else {
                        _state.value = WeatherActions.Error
                    }
                } else {
                    _state.value = WeatherActions.Error
                }
            } catch (e: WeatherRequestGenericException) {
                _state.value = WeatherActions.Error
            }
        }
    }
}

sealed class WeatherActions {
    object Idle : WeatherActions()
    object Loading : WeatherActions()
    data class OnWeatherReceived(val weatherResponse: WeatherResponse) : WeatherActions()
    object Error : WeatherActions()
}