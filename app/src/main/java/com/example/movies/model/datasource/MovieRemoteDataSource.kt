package com.example.movies.model.datasource

import com.example.movies.model.RegionRepository
import com.example.movies.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}