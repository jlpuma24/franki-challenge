package com.frankichallenge.di

import androidx.room.Room
import com.frankichallenge.data.local.WeatherDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

    single { get<WeatherDatabase>().weatherDao() }
    single { get<WeatherDatabase>().weatherConditionDao() }
}