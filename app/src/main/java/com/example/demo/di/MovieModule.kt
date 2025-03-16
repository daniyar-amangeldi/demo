package com.example.demo.di

import com.example.data.repository.MovieRepositoryImpl
import com.example.data.source.remote.api.MovieApi
import com.example.data.source.remote.MovieRemoteDataSource
import com.example.data.util.network.NetworkConfig
import com.example.data.util.network.NetworkManager
import com.example.data.source.local.DatabaseProvider
import com.example.data.source.local.MovieDatabase
import com.example.data.source.local.dao.MovieDao
import com.example.demo.viewmodel.MovieDetailsViewModel
import com.example.demo.viewmodel.MovieViewModel
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.GetMovieList
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private const val AUTHORIZATION_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiODJjMTcyZTdiZjY2NjA1MTY4ODFjNmExZWQ2MTZkZCIsIm5iZiI6MTczMDU0MjY1MS41NDc5MDIzLCJzdWIiOiI1ZjkxYjEzYTU1YzFmNDAwMzkyNzk5YTIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.JQN6zlqcT5VT4E8HxJz_sytmEU9lQELBsRU5s4SEFXk"

val movieModule = module {
    viewModel {
        MovieDetailsViewModel()
    }

    viewModel {
        MovieViewModel(
            getMovieList = get<GetMovieList>()
        )
    }

    factory<GetMovieList> {
        GetMovieList(
            repository = get<MovieRepository>()
        )
    }

    single<MovieRepository> {
        MovieRepositoryImpl(
            remoteDataSource = get<MovieRemoteDataSource>()
        )
    }

    single<MovieRemoteDataSource> {
        MovieRemoteDataSource(
            api = get<MovieApi>()
        )
    }

    single<MovieApi> {
        NetworkManager.createService(
            config = get<NetworkConfig>()
        )
    }

    single<NetworkConfig> {
        NetworkConfig(
            baseUrl = "https://api.themoviedb.org/",
            timeout = 60L,
            headers = hashMapOf(
                "Authorization" to "Bearer $AUTHORIZATION_TOKEN"
            ),
            interceptors = listOf()
        )
    }

    single<MovieDao> {
        get<DatabaseProvider>().movieDao
    }

    single<DatabaseProvider> {
        MovieDatabase(androidContext())
    }
}