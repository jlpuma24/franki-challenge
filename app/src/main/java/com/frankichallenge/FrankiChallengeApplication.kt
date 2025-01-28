package com.frankichallenge

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.frankichallenge.di.applicationModules
import com.frankichallenge.di.databaseModule
import com.frankichallenge.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FrankiChallengeApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FrankiChallengeApplication)
            modules (
                databaseModule,
                networkModule,
                applicationModules
            )
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}