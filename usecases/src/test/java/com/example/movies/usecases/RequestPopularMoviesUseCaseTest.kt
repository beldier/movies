package com.example.movies.usecases

import com.example.movies.data.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val moviesRepository = mock<MoviesRepository>()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)

        requestPopularMoviesUseCase()

        verify(moviesRepository).requestPopularMovies()
    }
}