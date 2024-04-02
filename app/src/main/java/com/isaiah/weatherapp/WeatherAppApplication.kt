package com.isaiah.weatherapp

import android.app.Application
import ke.co.equitybank.oneequity.features.borrow.get_loan.di.GetWeatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfigHelper.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@WeatherAppApplication)
            modules(
                GetWeatherModule.module
            )
        }
    }
}