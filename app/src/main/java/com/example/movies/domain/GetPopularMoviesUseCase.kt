package com.example.movies.domain

import com.example.movies.model.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}