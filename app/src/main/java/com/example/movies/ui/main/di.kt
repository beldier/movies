package com.example.movies.ui.main

import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelFactoryProvider(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)
}

@Subcomponent(modules = [(MainFragmentModule::class)])
interface MainFragmentComponent {
    val mainViewModelFactory: MainViewModelFactory
}