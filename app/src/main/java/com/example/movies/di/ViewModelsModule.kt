package com.example.movies.di

import com.example.movies.ui.detail.DetailViewModelFactory
import com.example.movies.ui.main.MainViewModelFactory
import com.example.movies.usecases.FindMovieUseCase
import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import com.example.movies.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides


@Module
object ViewModelsModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)

    @Provides
    fun provideDetailViewModel(
        findMovieById: FindMovieUseCase,
        toggleMovieFavorite: SwitchMovieFavoriteUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(
            movieId = -1,
            findMovieUseCase = findMovieById,
            switchMovieFavoriteUseCase = toggleMovieFavorite
        )
    }
}
