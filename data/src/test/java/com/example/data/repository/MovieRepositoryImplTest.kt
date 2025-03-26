package com.example.data.repository

import com.example.data.model.MovieListResponse
import com.example.data.model.MovieResponse
import com.example.data.source.local.MovieLocalDataSource
import com.example.data.source.remote.MovieRemoteDataSource
import com.example.domain.model.Genre
import com.example.domain.model.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieRepositoryImplTest {

    @Test
    fun getMovieList_shouldFetchFromRemote_whenForceIsTrue() = runTest {
        // Arrange
        val remoteDataSource = mock<MovieRemoteDataSource>()
        val localDataSource = mock<MovieLocalDataSource>()
        val repository = MovieRepositoryImpl(remoteDataSource, localDataSource)

        val mockResponseMovies = listOf(
            MovieResponse(id = 1, title = "Test Movie", voteAverage = 8.5, posterPath = "/poster.jpg", genres = listOf(12, 14))
        )
        val mockResponse = MovieListResponse(mockResponseMovies)

        whenever(remoteDataSource.fetchMovieList()).thenReturn(mockResponse)

        val expectedMovies = mockResponseMovies.map {
            Movie(
                id = it.id.toString(),
                title = it.title,
                rating = it.voteAverage,
                genre = it.genres.map { Genre.fromId(it)?.displayName.orEmpty() },
                duration = 120,
                imageUrl = it.posterPath
            )
        }

        // Act
        val result = repository.getMovieList(true)

        // Assert
        verify(remoteDataSource).fetchMovieList()
        verify(localDataSource, never()).fetchMovieList()

        assertTrue(result.isSuccess)
        assertEquals(expectedMovies, result.getOrNull())
    }
}