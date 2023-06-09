package com.example.movies.model

import com.example.movies.App
import com.example.movies.model.database.Movie
import com.example.movies.model.datasource.MovieLocalDataSource
import com.example.movies.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        com.example.movies.BuildConfig.apiKey
    )

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies() {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies.results.toLocalModel())
        }
    }
}

private fun List<RemoteMovie>.toLocalModel(): List<Movie> = map { it.toLocalModel() }

private fun RemoteMovie.toLocalModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage
)