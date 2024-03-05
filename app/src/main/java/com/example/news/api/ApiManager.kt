package com.example.news.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    //OkHttpClient -> create and addInterceptor()
    // HttpLoggingInterceptor create and pass a Logger object to it
    // HttpLoggingInterceptor set its level
    // pass OkHttpClient as client to retrofit

    private const val BASE_URL = "https://newsapi.org"
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.e(
                "API_Log@@@",
                message
            )
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLoggingInterceptor()).build()

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val newsService = retrofit.create(NewsService::class.java)
    val sourcesService = retrofit.create(SourcesService::class.java)
}
