package com.example.movies.useCase

import com.example.movies.data.Error
import com.example.movies.data.MoviesRepository


class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}