package com.example.movies.usecases

import com.example.movies.data.MoviesRepository

class GetPopularMoviesUseCase(private val repository: com.example.movies.data.MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}