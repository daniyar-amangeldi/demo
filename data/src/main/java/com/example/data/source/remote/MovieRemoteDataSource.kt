package com.example.data.source.remote

import com.example.data.source.remote.api.MovieApi

class MovieRemoteDataSource(private val api: MovieApi) {

    suspend fun fetchMovieList() = api.fetchMovieList()
}