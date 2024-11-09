package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demo.model.datasource.ApiSource
import com.example.demo.view.adapter.MovieAdapter
import com.example.demo.model.entity.MovieListResponse
import com.example.demo.R
import com.example.demo.databinding.FragmentMovieListBinding
import com.example.demo.model.entity.movieMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!

    private var adapter: MovieAdapter? = null

    companion object {

        fun newInstance() = MovieListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(
            onMovieClickListener = {
                val movieDetailsFragment = MovieDetailsFragment.newInstance(it.title)

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, movieDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            },
            onChangeFavouriteState = { movie, isFavourite ->
                changeFavouriteState(movie.id, isFavourite)
            }
        )

        binding.recyclerView.adapter = adapter

        ApiSource.client.fetchMovieListFromTMDB().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(p0: Call<MovieListResponse>, p1: Response<MovieListResponse>) {
                println("RetrofitRequest: ${p1.body()}")

                val movieList = p1.body()

                if (movieList != null) {
                    adapter?.submitList(movieList.results?.map(movieMapper))
                }
            }

            override fun onFailure(p0: Call<MovieListResponse>, p1: Throwable) {
                println("RetrofitRequest: ${p1}")
            }

        })
    }

    private fun changeFavouriteState(movieId: String, isFavourite: Boolean) {
        // TODO: Handle favourite state logic
    }
}