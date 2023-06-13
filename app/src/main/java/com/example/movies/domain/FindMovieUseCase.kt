package com.example.movies.domain

import com.example.movies.model.MoviesRepository

class FindMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}