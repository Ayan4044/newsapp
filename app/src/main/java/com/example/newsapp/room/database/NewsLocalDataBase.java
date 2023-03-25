package com.example.newsapp.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.newsapp.room.dao.RoomDao;
import com.example.newsapp.room.model.DataClassNewsLocal;

@Database(entities= DataClassNewsLocal.class,version=1)
public abstract  class NewsLocalDataBase extends RoomDatabase {
    public abstract RoomDao newsDao();
    public  static volatile NewsLocalDataBase newsLocalDataBase;
    public static NewsLocalDataBase getDatabase(final Context context)
    {
        if(newsLocalDataBase==null)
        {
            newsLocalDataBase=Room.databaseBuilder(context.getApplicationContext(),NewsLocalDataBase.class,"news_database").build();
        }
        return  newsLocalDataBase;
    }
}
