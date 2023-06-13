package com.example.movies.domain

import com.example.movies.model.MoviesRepository
import com.example.movies.model.database.Movie


class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie) = repository.switchFavorite(movie)
}