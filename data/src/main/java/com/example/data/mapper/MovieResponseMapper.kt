package com.example.data.mapper

import com.example.data.model.MovieEntity
import com.example.data.model.MovieResponse
import com.example.domain.model.Genre
import com.example.domain.model.Movie

val movieResponseMapper: (MovieResponse) -> Movie = { response ->
    Movie(
        id = response.id.toString(),
        title = response.title,
        rating = response.voteAverage,
        duration = 120,
        imageUrl = response.posterPath,
        genre = response.genres.map {
            Genre.fromId(it)?.displayName ?: "Horror"
        }
    )
}

val localMovieResponseMapper: (MovieEntity) -> Movie = { response ->
    Movie(
        id = response.id,
        title = response.title,
        rating = response.rating,
        duration = 120,
        imageUrl = response.imageUrl,
        genre = listOf(response.genre)
    )
}

val movieToMovieEntityMapper: (Movie) -> MovieEntity = { movie ->
    MovieEntity(
        id = movie.id,
        title = movie.title,
        rating = movie.rating,
        genre = movie.genre.first(),
        duration = movie.duration,
        imageUrl = movie.imageUrl,
    )
}