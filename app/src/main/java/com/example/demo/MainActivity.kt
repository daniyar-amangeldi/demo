package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var adapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(
            onMovieClickListener = {
                val intent = Intent(this, MovieDetails::class.java)
                intent.putExtra("title", it.title)

                startActivity(intent)
            },
            onChangeFavouriteState = { movie, isFavourite ->
                changeFavouriteState(movie.id, isFavourite)
            }
        )

        binding.recyclerView.adapter = adapter

        adapter?.submitList(DataSource.movieList)
    }

    private fun changeFavouriteState(movieId: String, isFavourite: Boolean) {
        adapter?.submitList(
            if (isFavourite) {
                DataSource.setFavourite(movieId)
            } else {
                DataSource.unsetFavourite(movieId)
            }
        )
    }
}