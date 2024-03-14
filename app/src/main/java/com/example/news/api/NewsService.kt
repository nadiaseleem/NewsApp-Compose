package com.example.news.api

import com.example.news.model.article.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/everything")
    fun getNews(@Query("sources") sourceId:String,@Query("apiKey")apiKey:String=ApiConstants.getApiKey()): Call<NewsResponse>

    @GET("/v2/everything")
    fun getArticle(@Query("q")title:String,@Query("searchIn")field:String,@Query("apiKey")apiKey:String=ApiConstants.getApiKey()):Call<NewsResponse>

    @GET("/v2/everything")
    fun getSearchedArticles(@Query("q")searchQuery:String,@Query("apiKey")apiKey:String=ApiConstants.getApiKey()):Call<NewsResponse>

}
