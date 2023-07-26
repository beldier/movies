package com.example.movies

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movies.data.MoviesRepository
import com.example.movies.data.RegionRepository
import com.example.movies.data.database.MovieDatabase
import com.example.movies.usecases.FindMovieUseCase
import com.example.movies.usecases.GetPopularMoviesUseCase
import com.example.movies.usecases.RequestPopularMoviesUseCase
import com.example.movies.usecases.SwitchMovieFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.ksp.generated.module


fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(AppModule().module, dataModule, useCasesModule,  )
    }
}

@Module
@ComponentScan
class AppModule {

    @Single
    @Named("apiKey")
    fun apiKey(ctx: Context) = ctx.getString(R.string.api_key)

    @Single
    fun movieDatabase(ctx: Context) = Room.databaseBuilder(
        ctx,
        MovieDatabase::class.java, "movie-db"
    ).build()

    @Single
    fun movieDao(db: MovieDatabase) = db.movieDao()
}

private val dataModule = module {
    factoryOf(::RegionRepository)
    factoryOf(::MoviesRepository)
}

private val useCasesModule = module {
    factoryOf( ::GetPopularMoviesUseCase)
    factoryOf( ::RequestPopularMoviesUseCase)
    factoryOf( ::FindMovieUseCase)
    factoryOf( ::SwitchMovieFavoriteUseCase)
}