package com.example.movies.model

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.App
import com.example.movies.model.database.Movie
import com.example.movies.model.datasource.MovieLocalDataSource
import com.example.movies.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        com.example.movies.BuildConfig.apiKey,
        regionRepository = RegionRepository(application)
    )

    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
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