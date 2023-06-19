package com.example.movies.data.datasource

import com.example.movies.data.database.Movie as DbMovie
import com.example.movies.data.database.MovieDao
import com.example.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class MovieLocalDataSource(private val movieDao: MovieDao) {

    val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    suspend fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies.fromDomainModel())
    }
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

private fun List<Movie>.fromDomainModel(): List<DbMovie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): DbMovie = DbMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)