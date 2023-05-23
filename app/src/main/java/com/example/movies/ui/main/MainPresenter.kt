package com.example.movies.ui.main

import com.example.movies.model.Movie
import com.example.movies.model.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainPresenter(
    private val moviesRepository: MoviesRepository,
    private val scope: CoroutineScope
) {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(movies: List<Movie>)
        fun navigateTo(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view

        scope.launch {
            view.showProgress()
            view.updateData(moviesRepository.findPopularMovies().results)
            view.hideProgress()
        }
    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }

    fun onDestroy() {
        this.view = null
    }
}