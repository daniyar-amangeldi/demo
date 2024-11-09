package com.example.demo.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demo.model.entity.Movie

class MovieItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}