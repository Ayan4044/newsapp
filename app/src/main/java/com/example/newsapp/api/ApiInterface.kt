package com.example.newsapp.api

import com.example.newsapp.model.DataClassNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("latest_headlines?countries=in")
    fun loadNews(@Header("x-api-key") apikey:String,@Query("lang") news:String,  @Query("topic") lang:String):Call<DataClassNews>
}