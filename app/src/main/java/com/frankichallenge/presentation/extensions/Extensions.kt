package com.frankichallenge.presentation.extensions

import com.frankichallenge.BuildConfig

fun Double.kelvinToCelsius(): String {
    return "${(this - 273.15).toInt()} °C"
}

fun String.toWeatherIconUrl(): String {
    return "${BuildConfig.ICON_BASE_URL}${this}@2x.png"
}