package com.example.demo.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import com.example.demo.databinding.ViewGenreBinding
import com.example.demo.databinding.ViewGenreListBinding

class GenreListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewGenreListBinding

    init {
        binding = ViewGenreListBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    fun set(genreList: List<String>) {
        genreList.map { genre ->
            ViewGenreBinding.inflate(
                LayoutInflater.from(context), binding.genreList, true
            ).apply {
                movieGenre.text = genre
            }.root
        }
    }
}