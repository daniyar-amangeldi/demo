package com.example.demo.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val rating: Double,
    val genre: String,
    val duration: Int,
    @ColumnInfo("image_url") val imageUrl: String
) {

    companion object {
        fun from(movie: Movie) = MovieEntity(
            id = movie.id,
            title = movie.title,
            rating = movie.rating,
            genre = movie.genre.first(),
            duration = movie.duration,
            imageUrl = movie.imageUrl
        )
    }
}