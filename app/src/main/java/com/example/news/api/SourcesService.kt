package com.example.news.api

import com.example.news.model.source.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SourcesService {

    @GET("/v2/top-headlines/sources")
    fun getSources(@Query("category")category:String,@Query("apiKey") apiKey:String): Call<SourcesResponse>
}
