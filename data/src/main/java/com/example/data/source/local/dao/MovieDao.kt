package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert
    suspend fun insert(movieEntity: MovieEntity)
}