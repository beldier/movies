package com.example.movies.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailBinding
import com.example.movies.ui.common.app
import com.example.movies.ui.common.launchAndCollect


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private lateinit var component: DetailFragmentComponent

    private val viewModel: DetailViewModel by viewModels { component.detailViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(DetailFragmentModule(safeArgs.movieId))
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