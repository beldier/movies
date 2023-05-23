package com.example.movies.ui.main


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.model.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    if (movies != null) {
        (adapter as? MoviesAdapter)?.submitList(movies)
    }
}