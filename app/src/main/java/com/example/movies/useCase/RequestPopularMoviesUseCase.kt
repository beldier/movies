package com.example.movies.useCase

import com.example.movies.domain.Error
import com.example.movies.data.MoviesRepository


class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}