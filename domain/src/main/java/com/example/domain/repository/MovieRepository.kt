package com.example.domain.repository

import com.example.domain.model.Movie

interface MovieRepository {
    suspend fun getMovieList(): Result<List<Movie>>
}