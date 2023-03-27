package com.example.newsapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.room.dao.NewsDao
import com.example.newsapp.room.model.News

@Database(entities = [News::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao?

    companion object {
        @Volatile
        var localDatabase: LocalDatabase? = null
        fun getDatabase(context: Context): LocalDatabase? {
            if (localDatabase == null) {
                localDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "news_db"
                ).build()
            }
            return localDatabase
        }
    }
}