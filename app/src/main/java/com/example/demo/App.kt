package com.example.demo

import android.app.Application
import com.example.demo.di.movieModule
import com.example.demo.view.util.RemoteConfigManager
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@App)
            modules(movieModule)
        }

        RemoteConfigManager(this).start()
    }
}