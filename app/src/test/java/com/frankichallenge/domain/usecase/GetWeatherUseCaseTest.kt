package com.frankichallenge.domain.usecase

import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetWeatherUseCaseTest {

    private val weatherRepository: WeatherRepository = mockk()
    private val getWeatherUseCase = GetWeatherUseCase(weatherRepository)

    @Test
    fun `execute returns success when repository returns data`() = runTest {
        val mockWeatherResponse = WeatherResponse(
            cityName = "Atlanta",
            weather = emptyList(),
            main = mockk()
        )
        coEvery { weatherRepository.getWeather() } returns mockWeatherResponse

        val result = getWeatherUseCase.execute()

        assert(result.isSuccess)
        assertEquals(mockWeatherResponse, result.getOrNull())
    }

    @Test
    fun `execute returns failure when repository throws exception`() = runTest {
        val exception = Exception("Network Error")
        coEvery { weatherRepository.getWeather() } throws exception

        val result = getWeatherUseCase.execute()

        assert(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
