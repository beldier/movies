package com.example.movies

import android.app.Application
import androidx.room.Room
import com.example.movies.data.database.MovieDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()