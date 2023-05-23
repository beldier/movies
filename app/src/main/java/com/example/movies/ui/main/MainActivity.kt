package com.example.movies.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.model.MoviesRepository
import com.example.movies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val moviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            recycler.adapter = adapter

            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                adapter.submitList(moviesRepository.findPopularMovies().results)
                progress.visibility = View.GONE
            }
        }
    }
}