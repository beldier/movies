package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import javax.inject.Inject


class SwitchMovieFavoriteUseCase @Inject constructor(private val repository: com.example.movies.data.MoviesRepository) {

    suspend operator fun invoke(movie: com.example.movies.domain.Movie) = repository.switchFavorite(movie)
}