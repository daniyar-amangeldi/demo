package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.MovieEntity
import com.example.data.source.local.dao.MovieDao

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}