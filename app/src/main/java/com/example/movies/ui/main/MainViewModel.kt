package com.example.movies.ui.main

import androidx.lifecycle.*
import com.example.movies.model.database.Movie
import com.example.movies.model.MoviesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.popularMovies.collect { movies ->
                _state.value = UiState(movies = movies)
            }
        }
    }


    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.requestPopularMovies()
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
    )
}

class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}