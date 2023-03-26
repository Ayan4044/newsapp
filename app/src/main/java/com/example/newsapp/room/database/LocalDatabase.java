package com.example.newsapp.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsapp.room.dao.NewsDao;
import com.example.newsapp.room.model.News;

@Database(entities= News.class,version=1)
public abstract class LocalDatabase extends RoomDatabase {

    public  abstract NewsDao newsDao();

    public  static  volatile  LocalDatabase localDatabase;

    public static LocalDatabase getDatabase(final Context context)
    {
        if(localDatabase==null)
        {
            localDatabase=Room.databaseBuilder(context.getApplicationContext(),LocalDatabase.class,"news_db").build();
        }
        return  localDatabase;
    }


}
