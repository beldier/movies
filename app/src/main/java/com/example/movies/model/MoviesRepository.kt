package com.example.movies.model

import android.app.Application
import androidx.appcompat.app.AppCompatActivity

class MoviesRepository(application: Application) {
    private val apiKey = com.example.movies.BuildConfig.apiKey
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}