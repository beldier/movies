package com.example.movies.model

import androidx.appcompat.app.AppCompatActivity

class MoviesRepository(activity: AppCompatActivity) {
    private val apiKey = com.example.movies.BuildConfig.apiKey
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}