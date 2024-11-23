package com.example.demo

import android.app.Application
import androidx.room.Room
import com.example.demo.model.dao.MigrationFrom1To2
import com.example.demo.model.db.AppDatabase

class App : Application() {

    companion object {
        private const val DATABASE_NAME = "demo_database"

        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}