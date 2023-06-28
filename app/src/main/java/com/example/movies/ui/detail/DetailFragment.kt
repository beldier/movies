package com.example.movies.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movies.BuildConfig
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailBinding
import com.example.movies.data.AndroidPermissionChecker
import com.example.movies.data.database.MovieRoomDataSource
import com.example.movies.data.server.MovieServerDataSource
import com.example.movies.data.PlayServicesLocationDataSource
import com.example.movies.ui.common.app
import com.example.movies.ui.common.launchAndCollect


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        val application = requireActivity().app
        val repository = com.example.movies.data.MoviesRepository(
            com.example.movies.data.RegionRepository(
                PlayServicesLocationDataSource(application),
                AndroidPermissionChecker(application)
            ),
            MovieRoomDataSource(application.db.movieDao()),
            MovieServerDataSource(BuildConfig.apiKey)
        )
        DetailViewModelFactory(
            safeArgs.movieId,
            com.example.movies.usecases.FindMovieUseCase(repository),
            com.example.movies.usecases.SwitchMovieFavoriteUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            if (state.movie != null) {
                binding.movie = state.movie
            }
        }
    }
}