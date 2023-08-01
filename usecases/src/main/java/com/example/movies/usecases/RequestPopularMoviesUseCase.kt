package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import com.example.movies.domain.Error
import javax.inject.Inject


class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularMovies()
    }
}