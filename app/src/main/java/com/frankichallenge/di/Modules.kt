package com.frankichallenge.di

import com.frankichallenge.data.repository.ConcreteWeatherRepository
import com.frankichallenge.domain.repository.WeatherRepository
import com.frankichallenge.domain.usecase.GetWeatherUseCase
import com.frankichallenge.presentation.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val applicationModules = module {

    factory<WeatherRepository> {
        ConcreteWeatherRepository(
            apiService = get(),
            weatherDao = get(),
            weatherConditionDao = get()
        )
    }

    factory {
        GetWeatherUseCase(get())
    }

    viewModel { WeatherViewModel(get()) }
}