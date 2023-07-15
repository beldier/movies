package com.example.movies

import android.app.Application
import androidx.room.Room
import com.example.movies.data.database.MovieDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}