package com.example.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ItemMovieBinding

class MovieAdapter(
    private val onMovieClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val movieList = ArrayList<Movie>()

    fun setData(randomList: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(randomList)

        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = movieList.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
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
                movieGenre.text = movie.genre

                root.setOnClickListener {
                    onMovieClickListener(movie)
                }
            }
        }
    }
}