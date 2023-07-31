package com.example.movies.di

import com.example.movies.data.MoviesRepository
import com.example.movies.data.PermissionChecker
import com.example.movies.data.RegionRepository
import com.example.movies.data.datasource.LocationDataSource
import com.example.movies.data.datasource.MovieLocalDataSource
import com.example.movies.data.datasource.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun provideRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun provideMoviesRepository(
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource,
        regionRepository: RegionRepository
    ) = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}