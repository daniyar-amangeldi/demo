package com.example.domain.repository

import com.example.domain.model.Movie

interface MovieRepository {
    suspend fun getMovieList(force: Boolean): Result<List<Movie>>

    suspend fun insertMovieList(movieList: List<Movie>): Result<Unit>
}