package com.example.demo.di

import androidx.room.Room
import com.example.demo.App
import com.example.demo.model.api.MovieApi
import com.example.demo.model.dao.MovieDao
import com.example.demo.model.datasource.ApiSource
import com.example.demo.model.db.AppDatabase
import com.example.demo.viewmodel.MovieDetailsViewModel
import com.example.demo.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NAME = "demo_database"

val movieModule = module {
    viewModel {
        MovieDetailsViewModel(
            movieDao = get<MovieDao>()
        )
    }

    viewModel {
        MovieViewModel(
            movieDao = get<MovieDao>(),
            client = get<MovieApi>()
        )
    }

    single<MovieDao> {
        get<AppDatabase>().movieDao()
    }

    single<MovieApi> {
        ApiSource.retrofit.create(MovieApi::class.java)
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}