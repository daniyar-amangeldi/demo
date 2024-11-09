package com.example.demo.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.R
import com.example.demo.model.api.MovieApi
import com.example.demo.model.entity.Movie
import com.example.demo.model.entity.MovieListResponse
import com.example.demo.model.entity.movieMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class MovieViewmodel(
    private val client: MovieApi
): ViewModel() {

    private val _movieListUI = MutableLiveData<MovieListUI>()
    val movieListUI: LiveData<MovieListUI> = _movieListUI

    init{
        fetchPopularMovieList()
    }

    private fun fetchPopularMovieList() {
        _movieListUI.value = MovieListUI.Loading(true)

        client.fetchMovieListFromTMDB().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(p0: Call<MovieListResponse>, p1: Response<MovieListResponse>) {
                if (!p1.isSuccessful) {
                    _movieListUI.value = MovieListUI.Error(R.string.error_general)
                    return
                }

                val movieList = p1.body()

                if (movieList != null) {
                    _movieListUI.value = MovieListUI.Success(movieList.results.map(movieMapper))
//                    _movieListUI.value = MovieListUI.Loading(false)
                } else {
                    _movieListUI.value = MovieListUI.Empty
                }
            }

            override fun onFailure(p0: Call<MovieListResponse>, p1: Throwable) {
                _movieListUI.value = MovieListUI.Error(R.string.error_general)
            }
        })
    }
}

sealed interface MovieListUI {
    data class Loading(val isLoading: Boolean) : MovieListUI
    data class Error(@StringRes val errorMessage: Int) : MovieListUI
    data class Success(val movieList: List<Movie>) : MovieListUI
    data object Empty : MovieListUI
}