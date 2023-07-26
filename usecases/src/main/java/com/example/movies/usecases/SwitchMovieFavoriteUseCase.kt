package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import org.koin.core.annotation.Factory

@Factory
class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: com.example.movies.domain.Movie) = repository.switchFavorite(movie)
}