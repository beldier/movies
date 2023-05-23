package com.example.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ViewMovieBinding
import com.example.movies.model.Movie
import com.example.movies.ui.common.basicDiffUtil
import com.example.movies.ui.common.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(private val binding: ViewMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }
}