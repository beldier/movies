package com.example.movies.di

import android.app.Application
import com.example.movies.ui.detail.DetailFragmentComponent
import com.example.movies.ui.detail.DetailFragmentModule
import com.example.movies.ui.detail.DetailViewModelFactory
import com.example.movies.ui.main.MainFragmentComponent
import com.example.movies.ui.main.MainFragmentModule
import com.example.movies.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}