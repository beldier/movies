package com.example.movies.model.datasource

import com.example.movies.model.RegionRepository
import com.example.movies.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String, private val regionRepository: RegionRepository) {

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}