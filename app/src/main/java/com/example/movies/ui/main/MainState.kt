package com.example.movies.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.movies.domain.Error
import com.example.movies.domain.Movie


fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context,scope, navController, locationPermissionRequester)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester
) {
    fun onMovieClicked(movie: com.example.movies.domain.Movie) {
        val action = MainFragmentDirections.actionMainToDetail(movie.id)
        navController.navigate(action)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: com.example.movies.domain.Error) = when (error) {
        com.example.movies.domain.Error.Connectivity -> context.getString(R.string.connectivity_error)
        is com.example.movies.domain.Error.Server -> context.getString(R.string.server_error) + error.code
        is com.example.movies.domain.Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }


}