package com.example.movies.data.datasource

import arrow.core.Either
import com.example.movies.domain.Error
import com.example.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}