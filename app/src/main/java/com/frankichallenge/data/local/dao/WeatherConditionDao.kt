package com.frankichallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frankichallenge.data.entity.WeatherConditionEntity

@Dao
interface WeatherConditionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherCondition(condition: WeatherConditionEntity)

    @Query("SELECT * FROM weather_condition WHERE weatherId = :weatherId")
    suspend fun getWeatherConditions(weatherId: Int): List<WeatherConditionEntity>
}
