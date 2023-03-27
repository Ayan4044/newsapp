package com.example.newsapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.room.model.News

@Dao
interface NewsDao {
    @Insert
    fun insertNews(news: News?)

    //Li
    @get:Query("SELECT * FROM news_table")
    val allnews: LiveData<List<News?>?>?

    @get:Query("SELECT dataclassnews FROM news_table ORDER BY id DESC LIMIT 1")
    val lastSaved: LiveData<String?>?
}