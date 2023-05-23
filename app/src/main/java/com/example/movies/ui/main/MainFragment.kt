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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }

    private lateinit var mainState: MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
//        viewLifecycleOwner.launchAndCollect(
//            viewModel.state.map { it.loading }.distinctUntilChanged()
//        ) { visible ->
//            binding.progress.visible = visible
//        }
//
//        viewLifecycleOwner.launchAndCollect(
//            viewModel.state.map { it.movies }.distinctUntilChanged()
//        ) { movies ->
//            adapter.submitList(movies)
//        }

        with(viewModel.state) {
            diff({ it.movies }) { it?.let(adapter::submitList) }
            diff({ it.loading }) { binding.progress.visible = it }
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }

    private fun <T, U> Flow<T>.diff(mapFlow: (T) -> U, body: (U) -> Unit) {
        viewLifecycleOwner.launchAndCollect(
            flow = map(mapFlow).distinctUntilChanged(),
            body = body
        )
    }
}