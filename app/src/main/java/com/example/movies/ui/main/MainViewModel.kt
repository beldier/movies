package com.example.movies.ui.main

import androidx.lifecycle.*
import com.example.movies.model.Movie
import com.example.movies.model.MoviesRepository
import kotlinx.coroutines.launch


class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState>
        get() {
        if(_state.value?.movies == null){
            refresh()
        }
        return _state
    }

    private fun refresh(){
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClicked(movie: Movie){
        _state.value = _state.value?.copy(navigateTo = movie)
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val navigateTo: Movie? = null
    )
}

class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}