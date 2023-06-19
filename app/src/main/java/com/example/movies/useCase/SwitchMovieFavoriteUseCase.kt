package com.example.movies.useCase

import com.example.movies.data.MoviesRepository
import com.example.movies.data.database.Movie


class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie) = repository.switchFavorite(movie)
}