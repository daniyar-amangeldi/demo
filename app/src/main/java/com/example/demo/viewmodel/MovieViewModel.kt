package com.example.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.usecase.GetMovieList
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMovieList: GetMovieList
) : ViewModel() {

    private val _movieListUI = MutableLiveData<MovieListUI>()
    val movieListUI: LiveData<MovieListUI> = _movieListUI

    fun changeFavouriteState(movie: Movie, isFavourite: Boolean) {
        // TODO: set favourite state
    }

    fun fetchPopularMovieList() {
        viewModelScope.launch {
            _movieListUI.value = MovieListUI.Loading(true)

            getMovieList.run(Unit).fold(
                onSuccess = { movieList ->
                    if (movieList.isEmpty()) {
                        _movieListUI.value = MovieListUI.Empty
                    } else {
                        _movieListUI.value = MovieListUI.Success(movieList)
                    }
                },
                onFailure = {
                    _movieListUI.value = MovieListUI.Error(it.message)
                }
            ).also {
                _movieListUI.value = MovieListUI.Loading(false)
            }
        }
    }
}

sealed interface MovieListUI {
    data class Loading(val isLoading: Boolean) : MovieListUI
    data class Error(val message: String? = null) : MovieListUI
    data class Success(val movieList: List<Movie>) : MovieListUI
    data object Empty : MovieListUI
    data class MovieInserted(val movie: Movie) : MovieListUI
    data object MovieIsAlreadyFavourite : MovieListUI
}