package com.example.data.model

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
    @ColumnInfo("image_url") val imageUrl: String? = null
)