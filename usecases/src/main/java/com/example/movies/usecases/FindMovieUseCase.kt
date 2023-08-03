package com.example.movies.usecases

import javax.inject.Inject


class FindMovieUseCase @Inject constructor(private val repository: com.example.movies.data.MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}