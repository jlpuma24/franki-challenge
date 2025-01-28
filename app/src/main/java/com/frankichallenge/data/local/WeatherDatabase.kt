package com.frankichallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frankichallenge.data.entity.WeatherConditionEntity
import com.frankichallenge.data.entity.WeatherEntity
import com.frankichallenge.data.local.dao.WeatherConditionDao
import com.frankichallenge.data.local.dao.WeatherDao

@Database(
    entities = [WeatherEntity::class, WeatherConditionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun weatherConditionDao(): WeatherConditionDao
}
