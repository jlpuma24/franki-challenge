package com.frankichallenge.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    @SerializedName("name") val cityName: String,
)
