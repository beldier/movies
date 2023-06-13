package com.example.movies.domain

import com.example.movies.model.Error
import com.example.movies.model.MoviesRepository


class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}