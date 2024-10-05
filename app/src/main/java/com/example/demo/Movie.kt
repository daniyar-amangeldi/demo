package com.example.demo

import java.util.UUID

data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val rating: Double,
    val genre: String = "Horror",
    val duration: Int // duration in minutes
)
