package com.example.lembretes2

import android.app.Application
import com.example.lembretes2.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class LembreteApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@LembreteApp)
            modules(listOf(androidModule))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}