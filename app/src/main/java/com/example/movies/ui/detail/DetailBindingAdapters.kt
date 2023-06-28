package com.example.movies.ui.detail

import androidx.databinding.BindingAdapter
import com.example.movies.domain.Movie


@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: com.example.movies.domain.Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}