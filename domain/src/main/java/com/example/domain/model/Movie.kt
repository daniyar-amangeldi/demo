package com.example.domain.model

data class Movie(
    val id: String,
    val title: String,
    val rating: Double,
    val genre: List<String>,
    val duration: Int,
    val isFavourite: Boolean = false,
    val imageUrl: String? = null
)