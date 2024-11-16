package com.example.demo.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.model.api.MovieApi
import com.example.demo.model.entity.Genre
import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.movieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(
    private val client: MovieApi
) : ViewModel() {

    private val _movieListUI = MutableLiveData<MovieListUI>()
    val movieListUI: LiveData<MovieListUI> = _movieListUI

    fun fetchPopularMovieList() {
        _movieListUI.value = MovieListUI.Loading(true)

        viewModelScope.launch(Dispatchers.IO) {
            val movieListDeferred = async {
                client.fetchMovieList()
            }

            val genreListDeferred = async {
                client.fetchMovieGenres()
            }

            val movieList = movieListDeferred.await()
            val genreList = genreListDeferred.await()

            withContext(Dispatchers.Main) {
                println(genreList)

                if (movieList.results.isEmpty()) {
                    _movieListUI.value = MovieListUI.Empty
                } else {
                    _movieListUI.value = MovieListUI.Success(movieList.results.map(movieMapper))
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
}