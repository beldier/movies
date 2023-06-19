package com.example.movies.useCase

import com.example.movies.data.MoviesRepository

class FindMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}