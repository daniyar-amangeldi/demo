package com.example.data.source.remote

import com.example.data.model.MovieListResponse
import com.example.data.source.remote.api.MovieApi

interface MovieRemoteDataSource {
    suspend fun fetchMovieList(): MovieListResponse
}

class MovieRemoteDataSourceImpl(private val api: MovieApi) : MovieRemoteDataSource {

    override suspend fun fetchMovieList() = api.fetchMovieList()
}