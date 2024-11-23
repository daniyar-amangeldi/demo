package com.example.demo.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.model.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * from movie")
    fun getAllObservable(): LiveData<List<MovieEntity>>

    @Query("SELECT * from movie")
    fun getAllObservableFlow(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insert(movieEntity: MovieEntity)
}