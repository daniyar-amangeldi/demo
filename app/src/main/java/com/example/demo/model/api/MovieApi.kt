package com.example.demo.model.api

import com.example.demo.model.entity.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("3/movie/popular")
    fun fetchMovieListFromTMDB(): Call<MovieListResponse>
}