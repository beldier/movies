package com.example.movies.data

import com.example.movies.App
import com.example.movies.BuildConfig
import com.example.movies.framework.datasource.MovieRoomDataSource
import com.example.movies.framework.datasource.MovieServerDataSource
import com.example.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieRoomDataSource(application.db.movieDao())
    private val remoteDataSource = MovieServerDataSource(
        BuildConfig.apiKey
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
