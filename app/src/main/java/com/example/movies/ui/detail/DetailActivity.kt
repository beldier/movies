package com.example.movies.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.model.Movie
import com.example.movies.ui.common.loadUrl

class DetailActivity : AppCompatActivity() {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityDetailBinding.inflate(layoutInflater).run {
            setContentView(root)

            val movie = intent.getParcelableExtra<Movie>(MOVIE) ?: throw IllegalStateException()

            movieDetailToolbar.title = movie.title

            val background = movie.backdropPath ?: movie.posterPath
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$background")
            movieDetailSummary.text = movie.overview
            movieDetailInfo.setMovie(movie)
        }
    }
}