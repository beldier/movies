package com.example.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movies.data.toError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel(
    movieId: Int,
    findMovieUseCase: com.example.movies.usecases.FindMovieUseCase,
    private val switchMovieFavoriteUseCase: com.example.movies.usecases.SwitchMovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movie -> _state.update { UiState(movie = movie) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let { movie ->
                val error = switchMovieFavoriteUseCase(movie)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(val movie: com.example.movies.domain.Movie? = null, val error: com.example.movies.domain.Error? = null)
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val findMovieUseCase: com.example.movies.usecases.FindMovieUseCase,
    private val switchMovieFavoriteUseCase: com.example.movies.usecases.SwitchMovieFavoriteUseCase
    ) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase) as T
    }
}


@AssistedFactory
interface DetailViewModelAssistedFactory {
    fun create(movieId: Int): DetailViewModelFactory
}
