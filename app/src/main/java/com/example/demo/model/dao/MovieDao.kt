package com.example.demo.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.model.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insert(movieEntity: MovieEntity)
}