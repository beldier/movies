package com.example.movies.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.model.Movie
import com.example.movies.model.MoviesRepository
import com.example.movies.ui.common.visible
import com.example.movies.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::updateUI)
            }
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.visible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, movie)
        startActivity(intent)
    }
}