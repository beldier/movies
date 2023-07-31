package com.example.movies.di

import android.app.Application
import androidx.room.Room
import com.example.movies.R
import com.example.movies.data.AndroidPermissionChecker
import com.example.movies.data.PermissionChecker
import com.example.movies.data.PlayServicesLocationDataSource
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.database.MovieRoomDataSource
import com.example.movies.data.datasource.LocationDataSource
import com.example.movies.data.datasource.MovieLocalDataSource
import com.example.movies.data.datasource.MovieRemoteDataSource
import com.example.movies.data.server.MovieServerDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun providesDatabase(app:Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun provideLocalDataSource(db: MovieDatabase): MovieLocalDataSource =
        MovieRoomDataSource(db.movieDao())

    @Provides
    fun provideRemoteDataSource(@ApiKey apiKey: String): MovieRemoteDataSource =
        MovieServerDataSource(apiKey)

    @Provides
    fun provideLocationDataSource(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun providePermissionChecker(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}