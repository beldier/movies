package com.example.movies.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.model.Movie
import com.example.movies.ui.common.loadUrl

class DetailActivity : AppCompatActivity(), DetailPresenter.View {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie: Movie = requireNotNull(intent.getParcelableExtra(MOVIE))
        presenter.onCreate(this, movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(movie: Movie) = with(binding) {
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}