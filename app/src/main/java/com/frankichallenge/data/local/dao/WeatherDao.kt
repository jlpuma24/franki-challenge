package com.frankichallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frankichallenge.data.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity): Long

    @Query("SELECT * FROM weather WHERE cityName = :cityName LIMIT 1")
    suspend fun getWeatherByCity(cityName: String): WeatherEntity?
}
