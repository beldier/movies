package com.example.movies.data.database

import com.example.movies.data.datasource.MovieLocalDataSource
import com.example.movies.data.tryCall
import com.example.movies.domain.Error
import com.example.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao): MovieLocalDataSource {


    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDao.insertMovies(movies.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<com.example.movies.data.database.Movie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun com.example.movies.data.database.Movie.toDomainModel(): Movie = Movie(
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

private fun List<Movie>.fromDomainModel(): List<com.example.movies.data.database.Movie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): com.example.movies.data.database.Movie =
    com.example.movies.data.database.Movie(
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