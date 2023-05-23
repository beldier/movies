package com.example.movies.ui.main

import androidx.lifecycle.*
import com.example.movies.model.Movie
import com.example.movies.model.MoviesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClicked(movie: Movie) {
        viewModelScope.launch {
            _events.send(UiEvent.NavigateTo(movie))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null
    )

    sealed interface UiEvent {
        data class NavigateTo(val movie: Movie): UiEvent
    }
}
class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}