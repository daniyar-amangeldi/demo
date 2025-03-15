package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.domain.util.UseCase

class GetMovieList(
    private val repository: MovieRepository
) : UseCase<List<Movie>, Unit>() {

    override suspend fun run(params: Unit): Result<List<Movie>> {
        return repository.getMovieList()
    }
}