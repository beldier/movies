package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import org.koin.core.annotation.Factory

@Factory
class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}