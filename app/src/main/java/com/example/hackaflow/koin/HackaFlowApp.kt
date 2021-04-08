package com.example.hackaflow.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HackaFlowApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HackaFlowApp)
            modules(listOf(appModules))
        }
    }
}