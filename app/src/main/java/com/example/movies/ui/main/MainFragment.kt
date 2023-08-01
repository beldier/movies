package com.example.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.BuildConfig
import com.example.movies.R
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.ui.common.app

import com.example.movies.ui.common.launchAndCollect


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var component: MainFragmentComponent

    private val viewModel: MainViewModel by viewModels { component.mainViewModelFactory }

    private lateinit var mainState: MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(MainFragmentModule())
    }
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