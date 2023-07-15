package com.example.movies

import android.app.Application
import androidx.room.Room
import com.example.movies.data.AndroidPermissionChecker
import com.example.movies.data.MoviesRepository
import com.example.movies.data.PermissionChecker
import com.example.movies.data.PlayServicesLocationDataSource
import com.example.movies.data.RegionRepository
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.database.MovieRoomDataSource
import com.example.movies.data.datasource.LocationDataSource
import com.example.movies.data.datasource.MovieLocalDataSource
import com.example.movies.data.datasource.MovieRemoteDataSource
import com.example.movies.data.server.MovieServerDataSource
import com.example.movies.ui.detail.DetailViewModel
import com.example.movies.ui.main.MainViewModel
import com.example.movies.usecases.FindMovieUseCase
import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import com.example.movies.usecases.SwitchMovieFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(appModule, dataModule, useCasesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { BuildConfig.apiKey }
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
    single { get<MovieDatabase>().movieDao() }

    factory<MovieLocalDataSource> { MovieRoomDataSource(get()) }
    factory<MovieRemoteDataSource> { MovieServerDataSource(get(named("apiKey"))) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get()) }
}

private val useCasesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { RequestPopularMoviesUseCase(get()) }
    factory { FindMovieUseCase(get()) }
    factory { SwitchMovieFavoriteUseCase(get()) }
}