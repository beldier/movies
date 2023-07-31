package com.example.movies.di

import android.app.Application
import com.example.movies.ui.detail.DetailViewModelFactory
import com.example.movies.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UseCaseModule::class, DataModule::class, ViewModelsModule::class])
interface AppComponent {
    val mainViewModelFactory: MainViewModelFactory
    val detailViewModelFactory: DetailViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}