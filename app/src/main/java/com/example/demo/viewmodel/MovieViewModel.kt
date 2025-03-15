package com.example.demo.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.MovieRepository
import com.example.demo.model.api.MovieApi
import com.example.demo.model.dao.MovieDao
import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.MovieEntity
import com.example.demo.model.entity.movieEntityMapper
import com.example.demo.model.entity.movieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(
    private val client: MovieApi,
    private val movieDao: MovieDao
) : ViewModel() {

    private val _movieListUI = MutableLiveData<MovieListUI>()
    val movieListUI: LiveData<MovieListUI> = _movieListUI

    private val repository by lazy { MovieRepository(client, movieDao) }

    fun changeFavouriteState(movie: Movie, isFavourite: Boolean) {
        viewModelScope.launch {
            try {
                if (isFavourite) {
                    movieDao.insert(MovieEntity.from(movie))

                    _movieListUI.value = MovieListUI.MovieInserted(movie.copy(isFavourite = true))
                }
            } catch (e: Exception) {
                println("MovieInsertException: $e")

                _movieListUI.value = MovieListUI.MovieIsAlreadyFavourite
            }
        }
    }

    fun fetchPopularMovieList() {
        _movieListUI.value = MovieListUI.Loading(true)

        viewModelScope.launch(Dispatchers.IO) {
            println("RoomDatabase: ${movieDao.getAll()}")

            val movies = async {
                repository.getAllMovies()
            }

            val genreListDeferred = async {
                client.fetchMovieGenres()
            }

            val genreList = genreListDeferred.await()
            val movieList = movies.await()

            withContext(Dispatchers.Main) {
                println(genreList)

                if (movieList.isEmpty()) {
                    _movieListUI.value = MovieListUI.Empty
                } else {
                    _movieListUI.value = MovieListUI.Success(movieList)
                }

                _movieListUI.value = MovieListUI.Loading(false)
            }
        }
    }
}

sealed interface MovieListUI {
    data class Loading(val isLoading: Boolean) : MovieListUI
    data class Error(@StringRes val errorMessage: Int) : MovieListUI
    data class Success(val movieList: List<Movie>) : MovieListUI
    data object Empty : MovieListUI
    data class MovieInserted(val movie: Movie) : MovieListUI
    data object MovieIsAlreadyFavourite : MovieListUI
}