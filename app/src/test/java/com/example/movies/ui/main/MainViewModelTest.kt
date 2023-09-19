package com.example.movies.ui.main

import app.cash.turbine.test
import com.example.movies.testRules.CoroutinesTestRule
import com.example.movies.testShared.sampleMovie
import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var vm: MainViewModel

    private val movies = listOf(sampleMovie.copy(id = 1))

    @Before
    fun setup() {
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))
        vm = MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(MainViewModel.UiState(), awaitItem())
            assertEquals(MainViewModel.UiState(movies = movies), awaitItem())
            cancel()
        }
    }


    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting movies`() =
        runTest {
            vm.onUiReady()

            vm.state.test {
                assertEquals(MainViewModel.UiState(), awaitItem())
                assertEquals(MainViewModel.UiState(movies = movies), awaitItem())
                assertEquals(MainViewModel.UiState(movies = movies, loading = true), awaitItem())
                assertEquals(MainViewModel.UiState(movies = movies, loading = false), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Popular movies are requested when UI screen starts`() = runTest {
        vm.onUiReady()
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke()
    }
}