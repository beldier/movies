package com.example.movies.ui.detail

import androidx.databinding.BindingAdapter
import com.example.movies.model.database.Movie


@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}