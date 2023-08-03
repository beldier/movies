package com.example.movies.ui.main

import androidx.lifecycle.*
import com.example.movies.data.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(
    getPopularMoviesUseCase: com.example.movies.usecases.GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: com.example.movies.usecases.RequestPopularMoviesUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<com.example.movies.domain.Movie>? = null,
        val error: com.example.movies.domain.Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: com.example.movies.usecases.GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: com.example.movies.usecases.RequestPopularMoviesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase) as T
    }
}