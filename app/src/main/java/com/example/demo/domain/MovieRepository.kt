package com.example.demo.domain

import com.example.demo.model.api.MovieApi
import com.example.demo.model.dao.MovieDao
import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.movieEntityMapper
import com.example.demo.model.entity.movieMapper

class MovieRepository(private val api: MovieApi, private val movieDao: MovieDao) {

    suspend fun getAllMovies(): List<Movie> {
        val favMovies = getFavouriteMovies()
        return api.fetchMovieList().results.map(movieMapper).map {
            it.copy(isFavourite = it.title in favMovies.map { it.title })
        }
    }

    suspend fun getFavouriteMovies(): List<Movie> {
        return movieDao.getAll().map(movieEntityMapper)
    }

    suspend fun saveToFavourites(movie: Movie) {

    }
}