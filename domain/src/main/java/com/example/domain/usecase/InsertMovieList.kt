package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.domain.util.UseCase

class InsertMovieList(
    private val repository: MovieRepository
) : UseCase<Unit, List<Movie>>() {

    override suspend fun run(params: List<Movie>): Result<Unit> {
        return repository.insertMovieList(params)
    }
}