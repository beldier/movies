package com.example.movies.usecases


class FindMovieUseCase(private val repository: com.example.movies.data.MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}