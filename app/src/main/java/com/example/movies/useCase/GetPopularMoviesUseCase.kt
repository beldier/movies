package com.example.movies.useCase

import com.example.movies.data.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}