package com.example.movies.ui.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.model.MoviesRepository
import com.example.movies.ui.common.app
import com.example.movies.ui.common.launchAndCollect


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().app))
    }

    private lateinit var mainState: MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }


        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.movies = it.movies
            binding.error = it.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}