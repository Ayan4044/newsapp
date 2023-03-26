package com.example.newsapp.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapp.room.model.News;

import java.util.List;

@Dao
public interface NewsDao {

 @Insert
 void insertNews(News news);

 @Query("SELECT * FROM news_table")
 LiveData<List<News>> getAllnews(); //Li

@Query("SELECT dataclassnews FROM news_table ORDER BY id DESC LIMIT 1")
 LiveData<String> getLastSaved();
}
