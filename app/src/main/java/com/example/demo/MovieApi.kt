package com.example.demo

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("daniyar-amangeldi/fake-movie-api/movie_list")
    fun fetchMovieList(): Call<List<Movie>>
}