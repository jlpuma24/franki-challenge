package com.frankichallenge.presentation.screens.xml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.frankichallenge.R
import com.frankichallenge.data.model.WeatherResponse
import com.frankichallenge.databinding.ActivityMainBinding
import com.frankichallenge.presentation.extensions.kelvinToCelsius
import com.frankichallenge.presentation.extensions.toWeatherIconUrl
import com.frankichallenge.presentation.viewmodel.WeatherActions
import com.frankichallenge.presentation.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        listenToViewModel()
        weatherViewModel.getWeather()
    }

    private fun setupUI() {
        toggleViewsVisibility(false)
    }

    private fun listenToViewModel() {
        lifecycleScope.launch {
            weatherViewModel.state.collect { action ->
                when (action) {
                    is WeatherActions.Loading -> showLoading()
                    is WeatherActions.OnWeatherReceived -> displayWeather(action.weatherResponse)
                    is WeatherActions.Error -> showError()
                    else -> hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbLoader.isVisible = true
    }

    private fun hideLoading() {
        binding.pbLoader.isVisible = false
    }

    private fun displayWeather(weather: WeatherResponse) {
        hideLoading()
        updateWeatherUI(weather)
        toggleViewsVisibility(true)
    }

    private fun updateWeatherUI(weather: WeatherResponse) {
        binding.apply {
            weather.apply {
                tvCityName.text = cityName
                tvWeatherMain.text = this.weather.joinToString(", ") { it.main }
                val iconUrl = this.weather.firstOrNull()?.icon?.toWeatherIconUrl()
                Glide.with(this@MainActivity)
                    .load(iconUrl)
                    .error(R.drawable.error_placeholder)
                    .into(ivWeatherIcon)
                tvTemperature.text = main.temperature.kelvinToCelsius()
            }
        }
    }

    private fun toggleViewsVisibility(isVisible: Boolean) {
        binding.apply {
            tvCityName.isVisible = isVisible
            ivWeatherIcon.isVisible = isVisible
            tvWeatherMain.isVisible = isVisible
            tvTemperature.isVisible = isVisible
        }
    }

    private fun showError() {
        hideLoading()
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                weatherViewModel.getWeather()
            }
            .show()
    }
}
