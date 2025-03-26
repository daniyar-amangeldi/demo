package com.example.data.source.local

import com.example.data.model.MovieEntity
import com.example.data.source.local.dao.MovieDao

interface MovieLocalDataSource {
    suspend fun fetchMovieList(): List<MovieEntity>
    suspend fun insertMovieList(movieList: List<MovieEntity>)
}

class MovieLocalDataSourceImpl(private val dao: MovieDao) : MovieLocalDataSource{

    override suspend fun fetchMovieList() = dao.getAll()

    override suspend fun insertMovieList(movieList: List<MovieEntity>) = dao.insertAll(movieList)
}