package com.example.movies.usecases

import org.koin.core.annotation.Factory
import com.example.movies.data.MoviesRepository

@Factory
class FindMovieUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int) = repository.findById(id)
}