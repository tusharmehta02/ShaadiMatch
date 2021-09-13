package com.shaadi.assignment.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shaadi.assignment.data.model.local.ShaadiMatch

@Database(entities = [ShaadiMatch::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun shaadiMatchDao(): ShaadiMatchDao

    companion object {
        private lateinit var db: AppDataBase

        fun getDb(application: Application): AppDataBase {
            if (!::db.isInitialized) {
                db = Room.databaseBuilder(application, AppDataBase::class.java, "shaadi_com").build()
            }
            return db
        }
    }
}
