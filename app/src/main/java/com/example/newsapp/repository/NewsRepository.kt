package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitSingleton

class NewsRepository(
    private val retrofitGateway: RetrofitSingleton
) {

    fun getAllNews(authKey: String, topic: String) = retrofitGateway.instance.loadNews(authKey, topic)
}