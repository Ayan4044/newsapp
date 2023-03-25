package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.api.RetrofitSingleton
import com.example.newsapp.model.DataClassNews
import com.example.newsapp.room.dao.RoomDao
import com.example.newsapp.room.model.DataClassNewsLocal

class NewsRepository(
    private val retrofitGateway: RetrofitSingleton,
    private val roomDao: RoomDao
) {

    fun getAllNews(authKey: String, topic: String) = retrofitGateway.instance.loadNews(authKey, "en",topic)

    //room db queries
    suspend fun insertNews(dataclassNews: DataClassNewsLocal?) {
        roomDao.insert(dataclassNews)
    }

    val getCachedNews: LiveData<DataClassNewsLocal?> = roomDao.latestLocal

}