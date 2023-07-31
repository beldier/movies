package com.example.movies

import android.app.Application
import com.example.movies.di.AppComponent
import com.example.movies.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)
    }
}