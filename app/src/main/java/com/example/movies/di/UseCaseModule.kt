package com.example.movies.di

import com.example.movies.data.MoviesRepository
import com.example.movies.usecases.FindMovieUseCase
import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import com.example.movies.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        GetPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideRequestPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        RequestPopularMoviesUseCase(moviesRepository)
    @Provides
    fun provideFindMovieUseCase(moviesRepository: MoviesRepository) =
        FindMovieUseCase(moviesRepository)
    @Provides
    fun provideSwitchMovieFavoriteUseCase(moviesRepository: MoviesRepository) =
        SwitchMovieFavoriteUseCase(moviesRepository)
}