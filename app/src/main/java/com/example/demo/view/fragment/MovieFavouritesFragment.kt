package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.demo.R
import com.example.demo.viewmodel.MovieViewModelFactory
import com.example.demo.viewmodel.MovieViewmodel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFavouritesFragment : Fragment() {

    private val viewModel by activityViewModels<MovieViewmodel> { MovieViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favourites, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieFavourites.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MovieFavouritesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}