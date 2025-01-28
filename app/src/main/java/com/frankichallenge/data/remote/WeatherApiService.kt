package com.frankichallenge.data.remote

import com.frankichallenge.BuildConfig
import com.frankichallenge.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val DEFAULT_CITY = "Los Angeles"

interface WeatherApiService {

    @GET(BuildConfig.BASE_PATH)
    suspend fun getWeatherApiService(
        @Query("q") city: String = DEFAULT_CITY,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherResponse>

}