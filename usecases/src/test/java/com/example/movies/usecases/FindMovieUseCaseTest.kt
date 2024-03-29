package com.example.movies.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import com.example.movies.testShared.sampleMovie
class FindMovieUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val movie = flowOf(sampleMovie.copy(id = 1))
        val findMovieUseCase = FindMovieUseCase(mock() {
            on { findById(1) } doReturn (movie)
        })

        val result = findMovieUseCase(1)

        assertEquals(movie, result)
    }
}