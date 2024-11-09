package com.example.demo.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demo.view.fragment.MovieFavouritesFragment
import com.example.demo.view.fragment.MovieListFragment
import com.example.demo.view.fragment.ProfileFragment
import com.example.demo.R
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.viewmodel.MovieViewModelFactory
import com.example.demo.viewmodel.MovieViewmodel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieFavouritesFragment = MovieFavouritesFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()

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

        val movieListFragment = MovieListFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, movieListFragment)
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movie_list -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, movieListFragment)
                        .commit()
                }

                R.id.movie_favourites -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, movieFavouritesFragment)
                        .commit()
                }

                R.id.profile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, profileFragment)
                        .commit()

                }
            }

            true
        }
    }

}