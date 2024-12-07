package com.example.demo.view.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.databinding.ItemMovieBinding
import com.example.demo.model.entity.Movie

class MovieAdapter(
    private val onMovieClickListener: (Movie) -> Unit,
    private val onChangeFavouriteState: (Movie, Boolean) -> Unit
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieItemCallback()) {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("onBindViewHolder: $position")
        holder.bind(getItem(position))
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        fun bind(movie: Movie) {
            with(binding) {
                movieTitle.text = movie.title
                movieRating.text = "${movie.rating}/10 IMDb"
                movieDuration.text = "${movie.duration} min."
                genreListView.set(movie.genre)

                root.setOnClickListener {
                    onMovieClickListener(movie)
                }

                if (movie.isFavourite) {
                    favouriteImageView.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            root.context, R.color.red
                        )
                    )
                } else {
                    favouriteImageView.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            root.context, R.color.black
                        )
                    )
                }

                favouriteImageView.setOnClickListener {
                    onChangeFavouriteState(movie, !movie.isFavourite)
                }

                Glide
                    .with(root.context)
                    .load("https://image.tmdb.org/t/p/original" + movie.imageUrl)
                    .into(moviePoster);
            }
        }
    }
}