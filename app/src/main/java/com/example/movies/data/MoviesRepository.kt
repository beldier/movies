package com.example.movies.data

import com.example.movies.App
import com.example.movies.data.datasource.MovieLocalDataSource
import com.example.movies.data.datasource.MovieRemoteDataSource
import com.example.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        com.example.movies.BuildConfig.apiKey
    )

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies)
        }
    }
    suspend fun switchFavorite(movie: Movie): Error? = tryCall  {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }
}
