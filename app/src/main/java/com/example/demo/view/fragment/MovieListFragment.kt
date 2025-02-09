package com.example.demo.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.databinding.FragmentMovieListBinding
import com.example.demo.model.entity.Movie
import com.example.demo.util.ScreenshotManager
import com.example.demo.view.adapter.MovieAdapter
import com.example.demo.viewmodel.ApplicationViewModel
import com.example.demo.viewmodel.MovieListUI
import com.example.demo.viewmodel.MovieViewModel
import com.example.demo.viewmodel.MovieViewModelFactory

class MovieListFragment : Fragment() {

    companion object {
        private const val IMAGE_URI = "image_uri"
        private const val DELAY = 500L
        private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
        private const val INTENT_TYPE = "image/jpeg"
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!

    private var adapter: MovieAdapter? = null

    private val viewModel: MovieViewModel by lazy {
        MovieViewModelFactory().create(MovieViewModel::class.java)
    }

    private val applicationViewModel: ApplicationViewModel by activityViewModels<ApplicationViewModel>()

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

        binding.share.setOnClickListener {
            val screenshotUri = ScreenshotManager.takeScreenshot(binding.root) {
                Toast.makeText(requireContext(), "Instagram Share Failure", Toast.LENGTH_SHORT).show()
            }

            if (screenshotUri != null) {
                shareToInstagram(screenshotUri) {
                    Toast.makeText(requireContext(), "Instagram Share Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (applicationViewModel.movieId != null) {
            applicationViewModel.movieId?.let {
                val direction = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it)
                findNavController().navigate(direction)
            }
        }
    }

    private fun configureObserver() {
        viewModel.movieListUI.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieListUI.Success -> adapter?.submitList(state.movieList)
                is MovieListUI.Error -> handleError(state.errorMessage)
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

    private fun shareToInstagram(uri: Uri, onFailure: () -> Unit) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = INTENT_TYPE
                putExtra(Intent.EXTRA_STREAM, uri)
                setPackage(INSTAGRAM_PACKAGE_NAME)
            }
            startActivity(shareIntent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.parse("market://details?id=$INSTAGRAM_PACKAGE_NAME")
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                onFailure()
            }
        }
    }

    private fun handleEmptyState() {
        // TODO: Handle UI for case when there is no movie list
    }

    private fun handleError(@StringRes errorMessage: Int) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}