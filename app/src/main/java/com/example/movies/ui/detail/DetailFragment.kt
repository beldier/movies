package com.example.movies.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailBinding
import com.example.movies.ui.common.loadUrl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()


    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(requireNotNull(safeArgs.movie))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.movie = it.movie }
            }
        }
    }
}