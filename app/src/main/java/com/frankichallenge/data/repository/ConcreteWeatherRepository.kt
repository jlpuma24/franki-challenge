package com.frankichallenge.data.repository

import com.frankichallenge.data.entity.WeatherConditionEntity
import com.frankichallenge.data.entity.WeatherEntity
import com.frankichallenge.data.local.dao.WeatherConditionDao
import com.frankichallenge.data.local.dao.WeatherDao
import com.frankichallenge.data.model.Main
import com.frankichallenge.data.model.Weather
import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.data.remote.DEFAULT_CITY
import com.frankichallenge.data.remote.WeatherApiService
import com.frankichallenge.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConcreteWeatherRepository(
    private val apiService: WeatherApiService,
    private val weatherDao: WeatherDao,
    private val weatherConditionDao: WeatherConditionDao
) : WeatherRepository {

    override suspend fun getWeather(): WeatherResponse {
        return withContext(Dispatchers.IO) {
            val localWeather = weatherDao.getWeatherByCity(DEFAULT_CITY)

            if (localWeather != null) {
                val conditions = weatherConditionDao.getWeatherConditions(localWeather.id)
                WeatherResponse(
                    weather = conditions.map { Weather(it.main, it.icon) },
                    main = Main(localWeather.temperature),
                    cityName = localWeather.cityName
                )
            } else {
                val response = apiService.getWeatherApiService()
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val weatherId = weatherDao.insertWeather(
                            WeatherEntity(
                                cityName = weatherResponse.cityName,
                                temperature = weatherResponse.main.temperature
                            )
                        ).toInt()

                        weatherResponse.weather.forEach { condition ->
                            weatherConditionDao.insertWeatherCondition(
                                WeatherConditionEntity(
                                    weatherId = weatherId,
                                    main = condition.main,
                                    icon = condition.icon
                                )
                            )
                        }
                        weatherResponse
                    } ?: throw WeatherApiRequestGenericException()
                } else {
                    throw WeatherApiRequestGenericException()
                }
            }
        }
    }
}

open class WeatherApiRequestGenericException : Exception("Weather Api Request Exception")