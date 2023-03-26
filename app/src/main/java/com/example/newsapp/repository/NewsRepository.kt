package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.api.RetrofitSingleton
import com.example.newsapp.room.dao.NewsDao
import com.example.newsapp.room.model.News


class NewsRepository(
    private val retrofitGateway: RetrofitSingleton,
   private val roomDao: NewsDao
) {

    fun getAllNews(authKey: String, topic: String) = retrofitGateway.instance.loadNews(authKey, "en",topic)

    //room db queries
    suspend fun insertNews(dataclassNews: News?) {
        roomDao.insertNews(dataclassNews)
    }

    val getCachedNews: LiveData<List<News>?> = roomDao.allnews

    val getLastSavedNews: LiveData<String?> = roomDao.lastSaved


}