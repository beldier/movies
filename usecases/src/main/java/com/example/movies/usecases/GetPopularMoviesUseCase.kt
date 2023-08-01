package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: com.example.movies.data.MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}