package com.example.movies.data.server

import arrow.core.Either
import com.example.movies.data.datasource.MovieRemoteDataSource
import com.example.movies.data.tryCall
import com.example.movies.di.ApiKey
import com.example.movies.domain.Error
import com.example.movies.domain.Movie
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(@ApiKey private val apiKey: String) :
    MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        RemoteConnection.service
            .listPopularMovies(apiKey, region)
            .results
            .toDomainModel()
    }
}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }


private fun RemoteMovie.toDomainModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    "https://image.tmdb.org/t/p/w185/$posterPath",
    backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)