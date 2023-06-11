package com.example.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Error
import com.example.movies.model.MoviesRepository
import com.example.movies.model.database.Movie
import com.example.movies.model.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    movieId: Int,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.findById(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movie -> _state.update { UiState(movie = movie) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let { movie ->
                val error = repository.switchFavorite(movie)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(val movie: Movie? = null, val error: Error? = null)
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movieId: Int, private val repository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieId, repository) as T
    }
}