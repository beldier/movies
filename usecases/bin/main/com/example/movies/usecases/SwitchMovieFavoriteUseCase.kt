package com.example.movies.usecases

import com.example.movies.data.MoviesRepository


class SwitchMovieFavoriteUseCase(private val repository: com.example.movies.data.MoviesRepository) {

    suspend operator fun invoke(movie: com.example.movies.domain.Movie) = repository.switchFavorite(movie)
}