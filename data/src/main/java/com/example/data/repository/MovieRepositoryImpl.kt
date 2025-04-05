package com.example.data.repository

import com.example.data.mapper.localMovieResponseMapper
import com.example.data.mapper.movieResponseMapper
import com.example.data.mapper.movieToMovieEntityMapper
import com.example.data.source.local.MovieLocalDataSource
import com.example.data.source.remote.MovieRemoteDataSource
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.networkkit.repository.BaseRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : BaseRepository(), MovieRepository {

    override suspend fun getMovieList(force: Boolean): Result<List<Movie>> = safeApiCall({
        if (force) {
            remoteDataSource.fetchMovieList().results.map(movieResponseMapper)
        } else {
            val localMovieList = localDataSource.fetchMovieList()

            if (localMovieList.isEmpty()) {
                println("FetchType: Remote")
                remoteDataSource.fetchMovieList().results.map(movieResponseMapper)
            } else {
                println("FetchType: Local")
                localMovieList.map(localMovieResponseMapper)
            }
        }
    })

    override suspend fun insertMovieList(movieList: List<Movie>): Result<Unit> = safeApiCall({
        localDataSource.insertMovieList(movieList.map(movieToMovieEntityMapper))
    })
}