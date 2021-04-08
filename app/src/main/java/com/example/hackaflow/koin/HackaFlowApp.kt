package com.example.hackaflow.koin

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class HackaFlowApp: Application() {

    override fun onCreate() {
        super.onCreate()
        _appInstance = this
        startKoin {
            androidContext(this@HackaFlowApp)
            modules(listOf(appModules))
        }
    }

    companion object {
        private var _appInstance : HackaFlowApp? = null
        fun getString(id: Int) : String {
            return _appInstance!!.applicationContext.getString(id)
        }
    }
}