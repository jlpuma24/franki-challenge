package com.frankichallenge.domain.repository

import com.frankichallenge.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(): WeatherResponse
}