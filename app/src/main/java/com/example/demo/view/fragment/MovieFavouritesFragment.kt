package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.demo.databinding.FragmentMovieFavouritesBinding
import com.example.demo.model.entity.movieEntityMapper
import com.example.demo.view.adapter.MovieAdapter
import com.example.demo.viewmodel.MovieDetailsUI
import com.example.demo.viewmodel.MovieDetailsViewModel
import com.example.demo.viewmodel.MovieDetailsViewModelFactory
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieFavouritesFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFavouritesFragment()
    }

    private var _binding: FragmentMovieFavouritesBinding? = null
    private val binding: FragmentMovieFavouritesBinding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by lazy {
        MovieDetailsViewModelFactory().create(MovieDetailsViewModel::class.java)
    }

    private var adapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(
            onMovieClickListener = {
                // nothing to do
            },
            onChangeFavouriteState = { movie, isFavourite ->

            }
        )

        binding.recyclerView.adapter = adapter

        configureObserver()

        viewModel.fetchFavouriteMovieList()
    }

    private fun configureObserver() {
        lifecycleScope.launch {
            viewModel.movieListFlow.map { it.map { movieEntityMapper.invoke(it) } }.filterNotNull()
                .collect {
                    adapter?.submitList(it)
                }
        }
//        viewModel.movieListObservable.observe(viewLifecycleOwner){ list ->
//            list?.let { adapter?.submitList(it) }
//        }
//        viewModel.movieDetailsUI.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is MovieDetailsUI.Success -> adapter?.submitList(state.movieList)
//            }
//        }
    }
}