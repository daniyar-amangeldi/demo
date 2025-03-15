package com.example.data.model

data class GenreResponse(
    val genres: List<Item>
) {

    data class Item(
        val id: Int,
        val name: String
    )
}