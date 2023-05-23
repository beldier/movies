package com.example.movies.ui.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.model.Movie
import com.example.movies.model.MoviesRepository
import com.example.movies.ui.common.PermissionRequester
import com.example.movies.ui.common.launchAndCollect
import com.example.movies.ui.common.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }

    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }

    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.visible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
        if (state.requestLocationPermission) {
            requestLocationPermission()
        }
    }

    private fun navigateTo(movie: Movie) {
        val action = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(action)
        viewModel.onNavigateDone()
    }
    private fun requestLocationPermission() {
        viewLifecycleOwner.lifecycleScope.launch {
            coarsePermissionRequester.request()
            viewModel.onLocationPermissionChecked()
        }
    }
}