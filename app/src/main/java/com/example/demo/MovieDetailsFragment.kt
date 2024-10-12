package com.example.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.demo.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    companion object {
        private const val KEY_MOVIE_TITLE = "movie_title"

        fun newInstance(movieTitle: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(KEY_MOVIE_TITLE to movieTitle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieTitle.text = arguments?.getString(KEY_MOVIE_TITLE)
    }
}