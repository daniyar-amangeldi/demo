package com.example.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.model.dao.MovieDao
import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.movieEntityMapper
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieDao: MovieDao
) : ViewModel() {

    private val _movieDetailsUI = MutableLiveData<MovieDetailsUI>()
    val movieDetailsUI: LiveData<MovieDetailsUI> = _movieDetailsUI

    fun fetchFavouriteMovieList() {
        viewModelScope.launch {
            try {
                val movieList = movieDao.getAll()

                _movieDetailsUI.value = MovieDetailsUI.Success(
                    movieList.map(movieEntityMapper)
                )
            } catch (e: Exception) {
                println("MovieRetrieveError: $e")
            }
        }
    }
}

sealed interface MovieDetailsUI {
    data class Success(val movieList: List<Movie>) : MovieDetailsUI
}