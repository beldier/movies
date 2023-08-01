package com.example.movies.ui.detail

import com.example.movies.usecases.FindMovieUseCase
import com.example.movies.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val movieId: Int) {

    @Provides
    fun detailViewModelFactoryProvider(
        findMovieUseCase: FindMovieUseCase,
        switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) = DetailViewModelFactory(movieId, findMovieUseCase, switchMovieFavoriteUseCase)

}

@Subcomponent(modules = [(DetailFragmentModule::class)])
interface DetailFragmentComponent {
    val detailViewModelFactory: DetailViewModelFactory
}