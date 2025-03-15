package com.example.data.repository

import com.example.data.mapper.movieResponseMapper
import com.example.data.source.remote.MovieRemoteDataSource
import com.example.data.util.repository.BaseRepository
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : BaseRepository(), MovieRepository {

    override suspend fun getMovieList(): Result<List<Movie>> = safeApiCall({
        remoteDataSource.fetchMovieList().results.map(movieResponseMapper)
    })
}