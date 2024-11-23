package com.example.demo.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo.model.dao.MovieDao
import com.example.demo.model.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}