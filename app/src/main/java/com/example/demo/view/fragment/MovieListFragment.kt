package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.databinding.FragmentMovieListBinding
import com.example.demo.model.entity.Movie
import com.example.demo.view.adapter.MovieAdapter
import com.example.demo.viewmodel.MovieListUI
import com.example.demo.viewmodel.MovieViewModel
import com.example.demo.viewmodel.MovieViewModelFactory

class MovieListFragment : Fragment() {

    companion object {

        fun newInstance() = MovieListFragment()
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!

    private var adapter: MovieAdapter? = null

    private val viewModel: MovieViewModel by lazy {
        MovieViewModelFactory().create(MovieViewModel::class.java)
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
                viewModel.changeFavouriteState(movie, isFavourite)
            }
        )

        binding.recyclerView.adapter = adapter

        configureObserver()

        viewModel.fetchPopularMovieList()
    }

    private fun configureObserver() {
        viewModel.movieListUI.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieListUI.Success -> adapter?.submitList(state.movieList)
                is MovieListUI.Error -> handleError(state.errorMessage)
                is MovieListUI.Empty -> handleEmptyState()
                is MovieListUI.Loading -> binding.progressBar.isVisible = state.isLoading
                is MovieListUI.MovieInserted -> handleMovieInsert(state.movie)
            }
        }
    }

    private fun handleMovieInsert(movie: Movie) {
        Toast.makeText(requireContext(), "Movie saved!", Toast.LENGTH_SHORT).show()

        adapter?.submitList(
            adapter?.currentList?.map {
                if (it.id == movie.id) {
                    movie
                } else {
                    it
                }
            }
        )
    }

    private fun handleEmptyState() {
        // TODO: Handle UI for case when there is no movie list
    }

    private fun handleError(@StringRes errorMessage: Int) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}