package com.example.data.source.remote.api

import com.example.data.model.GenreResponse
import retrofit2.http.GET

interface MovieApi {

    @GET("3/movie/popular")
    suspend fun fetchMovieList(): com.example.data.model.MovieListResponse

    @GET("3/genre/movie/list")
    suspend fun fetchMovieGenres(): GenreResponse
}