package com.frankichallenge.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_condition")
data class WeatherConditionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weatherId: Int,
    val main: String,
    val icon: String
)