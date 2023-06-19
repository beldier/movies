package com.example.movies.ui.detail

import androidx.databinding.BindingAdapter
import com.example.movies.data.database.Movie


@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}