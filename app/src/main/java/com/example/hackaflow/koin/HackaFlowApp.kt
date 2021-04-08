package com.example.hackaflow.koin

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class HackaFlowApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HackaFlowApp)
            modules(listOf(appModules))
        }
    }
}