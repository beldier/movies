package com.example.movies.ui.main


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.domain.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<com.example.movies.domain.Movie>?) {
    if (movies != null) {
        (adapter as? MoviesAdapter)?.submitList(movies)
    }
}