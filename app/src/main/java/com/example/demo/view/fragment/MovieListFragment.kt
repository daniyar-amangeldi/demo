package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.demo.R
import com.example.demo.databinding.FragmentMovieListBinding
import com.example.demo.view.adapter.MovieAdapter
import com.example.demo.viewmodel.MovieListUI
import com.example.demo.viewmodel.MovieViewModel
import com.example.domain.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!

    private var adapter: MovieAdapter? = null

    private val viewModel: MovieViewModel by viewModel()

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
                val direction = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it.title)
                findNavController().navigate(direction)
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
                is MovieListUI.Error -> handleError(state.message)
                is MovieListUI.Empty -> handleEmptyState()
                is MovieListUI.Loading -> binding.progressBar.isVisible = state.isLoading
                is MovieListUI.MovieInserted -> handleMovieInsert(state.movie)
                is MovieListUI.MovieIsAlreadyFavourite -> Toast.makeText(
                    requireContext(), "Movie is already favourite", Toast.LENGTH_SHORT
                ).show()
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

    private fun handleError(message: String? = null) {
        Toast.makeText(requireContext(), message ?: getString(R.string.error_general), Toast.LENGTH_SHORT).show()
    }
}