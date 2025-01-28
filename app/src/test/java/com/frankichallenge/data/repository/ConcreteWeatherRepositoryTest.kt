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
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Response

class ConcreteWeatherRepositoryTest {

    private val apiService: WeatherApiService = mockk()
    private val weatherDao: WeatherDao = mockk()
    private val weatherConditionDao: WeatherConditionDao = mockk()

    private val repository: WeatherRepository =
        ConcreteWeatherRepository(apiService, weatherDao, weatherConditionDao)

    @Test
    fun `getWeather returns data from local database`() = runTest {
        val weatherEntity = WeatherEntity(cityName = "Atlanta", temperature = 295.0, id = 1)
        val weatherConditions =
            listOf(WeatherConditionEntity(weatherId = 1, main = "Cloudy", icon = "02d"))

        coEvery { weatherDao.getWeatherByCity(DEFAULT_CITY) } returns weatherEntity
        coEvery { weatherConditionDao.getWeatherConditions(1) } returns weatherConditions

        val result = repository.getWeather()

        assertEquals("Atlanta", result.cityName)
        assertEquals(295.0, result.main.temperature, 0.0)
        assertEquals(1, result.weather.size)
        assertEquals("Cloudy", result.weather[0].main)
        assertEquals("02d", result.weather[0].icon)

        verify(exactly = 0) { runBlocking { apiService.getWeatherApiService() } }
    }

    @Test
    fun `getWeather fetches from API and saves to database`() = runTest {

        coEvery { weatherDao.getWeatherByCity(DEFAULT_CITY) } returns null

        val apiResponse = WeatherResponse(
            cityName = "Atlanta",
            weather = listOf(Weather("Cloudy", "02d")),
            main = Main(295.0)
        )
        coEvery { apiService.getWeatherApiService() } returns Response.success(apiResponse)

        coEvery { weatherDao.insertWeather(any()) } returns 1L
        coEvery { weatherConditionDao.insertWeatherCondition(any()) } returns Unit

        val result = repository.getWeather()

        assertEquals("Atlanta", result.cityName)
        assertEquals(295.0, result.main.temperature, 0.0)
        assertEquals(1, result.weather.size)
        assertEquals("Cloudy", result.weather[0].main)
        assertEquals("02d", result.weather[0].icon)

        verify { runBlocking { weatherDao.insertWeather(any()) } }
        verify { runBlocking { weatherConditionDao.insertWeatherCondition(any()) } }
    }

    @Test
    fun `getWeather throws exception when API fails`() = runTest {
        coEvery { weatherDao.getWeatherByCity(DEFAULT_CITY) } returns null
        coEvery { apiService.getWeatherApiService() } returns Response.error(
            500,
            ResponseBody.create(null, "Internal Server Error")
        )

        val exception = assertThrows(WeatherApiRequestGenericException::class.java) {
            runBlocking {
                repository.getWeather()
            }
        }

        assertEquals("Weather Api Request Exception", exception.message)

        coVerify(exactly = 0) { weatherDao.insertWeather(any()) }
        coVerify(exactly = 0) { weatherConditionDao.insertWeatherCondition(any()) }
    }

}
