package com.example.data.source.local

import android.content.Context
import androidx.room.Room

interface DatabaseProvider {
    fun getDatabase(): AppDatabase
}

class DatabaseProviderImpl(private val context: Context) : DatabaseProvider {

    companion object {
        private const val DATABASE_NAME = "demo_database"
    }

    override fun getDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}