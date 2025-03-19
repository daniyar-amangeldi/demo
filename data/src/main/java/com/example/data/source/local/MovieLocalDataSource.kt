package com.example.data.source.local

import com.example.data.model.MovieEntity
import com.example.data.source.local.dao.MovieDao

class MovieLocalDataSource(private val dao: MovieDao) {

    suspend fun fetchMovieList() = dao.getAll()

    suspend fun insertMovieList(movieList: List<MovieEntity>) = dao.insertAll(movieList)
}