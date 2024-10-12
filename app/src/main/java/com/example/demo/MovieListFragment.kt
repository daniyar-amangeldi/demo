package com.example.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demo.databinding.FragmentMovieListBinding


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