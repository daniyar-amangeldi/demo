package com.example.demo.model.api

import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("daniyar-amangeldi/fake-movie-api/movie_list")
    fun fetchMovieList(): Call<List<Movie>>

    @GET("3/movie/popular")
    fun fetchMovieListFromTMDB(): Call<MovieListResponse>
}