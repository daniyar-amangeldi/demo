package com.example.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.MovieEntity
import com.example.data.source.local.dao.MovieDao

@Database(entities = [MovieEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

private const val DATABASE_NAME = "demo_database"

class DatabaseProvider internal constructor(private val database: AppDatabase) {

    val movieDao: MovieDao
        get() = database.movieDao()
}

fun MovieDatabase(context: Context): DatabaseProvider {
    val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    return DatabaseProvider(database)
}