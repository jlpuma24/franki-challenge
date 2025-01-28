package com.frankichallenge.domain.usecase

import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.domain.repository.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(): Result<WeatherResponse> {
        return try {
            val weatherResponse = weatherRepository.getWeather()
            Result.success(weatherResponse)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}

open class WeatherRequestGenericException : Exception("Weather Request Exception")