package com.frankichallenge.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frankichallenge.data.model.Main
import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.domain.usecase.GetWeatherUseCase
import com.frankichallenge.domain.usecase.WeatherRequestGenericException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherViewModel: WeatherViewModel
    private val getWeatherUseCase: GetWeatherUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        weatherViewModel = WeatherViewModel(getWeatherUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeather emits Idle, Loading, and OnWeatherReceived when successful`() = runTest {
        val mockWeatherResponse = WeatherResponse(
            cityName = "Atlanta",
            weather = emptyList(),
            main = Main(temperature = 282.46)
        )
        coEvery { getWeatherUseCase.execute() } returns Result.success(mockWeatherResponse)

        val states = mutableListOf<WeatherActions>()
        val job = launch {
            weatherViewModel.state.collect { states.add(it) }
        }

        weatherViewModel.getWeather()
        advanceUntilIdle()

        assertEquals(WeatherActions.Idle, states[0])
        assertEquals(WeatherActions.OnWeatherReceived(mockWeatherResponse), states[1])

        job.cancel()
    }


    @Test
    fun `getWeather emits Idle, Loading, and Error when use case fails`() = runTest {
        coEvery { getWeatherUseCase.execute() } returns Result.failure(Exception("Network error"))

        val states = mutableListOf<WeatherActions>()
        val job = launch {
            weatherViewModel.state.collect { states.add(it) }
        }

        weatherViewModel.getWeather()
        advanceUntilIdle()

        assertEquals(WeatherActions.Idle, states[0])
        assertEquals(WeatherActions.Error, states[1])

        job.cancel()
    }


    @Test
    fun `getWeather emits Error when exception occurs`() = runTest {
        // Mock exception
        coEvery { getWeatherUseCase.execute() } throws WeatherRequestGenericException()

        // Trigger the ViewModel function
        weatherViewModel.getWeather()
        advanceUntilIdle()

        // Assert the state
        assertEquals(WeatherActions.Error, weatherViewModel.state.first())
    }
}
