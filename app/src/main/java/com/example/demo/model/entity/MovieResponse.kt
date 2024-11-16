package com.example.demo.model.entity

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val results: List<MovieResponse>
)

data class MovieResponse(
    val id: Int,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("genre_ids") val genres: List<Int>
)
