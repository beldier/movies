package com.example.movies.data.datasource

import com.example.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<Movie>
}