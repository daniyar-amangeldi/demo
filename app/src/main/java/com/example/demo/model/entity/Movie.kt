package com.example.demo.model.entity

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val rating: Double,
    val genre: String = "Horror",
    val duration: Int, // duration in minutes
    val isFavourite: Boolean = false,
    @SerializedName("image_url") val imageUrl: String = "",
)

val movieMapper: (MovieResponse) -> Movie = { response ->
    Movie(
        id = response.id.toString(),
        title = response.title,
        rating = response.voteAverage,
        duration = 120,
        imageUrl = response.posterPath,
        genre = Genre.fromId(response.genres.first())?.displayName ?: "Horror"
    )
}

val movieEntityMapper: (MovieEntity) -> Movie = { response ->
    Movie(
        id = response.id,
        title = response.title,
        rating = response.rating,
        duration = 120,
        imageUrl = response.imageUrl,
        genre = response.genre,
        isFavourite = true
    )
}