package com.example.newsapp.api

import com.example.newsapp.model.DataClassNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiInterface {

    @GET("latest_headlines?countries=IN&topic={news}")
    fun loadNews(@Header("x-api-key") apikey:String, @Path("news") news:String):Call<DataClassNews>
}