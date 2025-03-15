package com.example.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Movie

class MovieDetailsViewModel(

) : ViewModel() {

    private val _movieDetailsUI = MutableLiveData<MovieDetailsUI>()
    val movieDetailsUI: LiveData<MovieDetailsUI> = _movieDetailsUI

    fun fetchFavouriteMovieList() {
        // TODO: fetch saved movie list
    }
}

sealed interface MovieDetailsUI {
    data class Success(val movieList: List<Movie>) : MovieDetailsUI
}