package com.example.news.fragments.news

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsItem
import com.example.news.model.article.NewsResponse
import com.example.news.model.source.SourceItem
import com.example.news.model.source.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel: ViewModel() {
    var newsList by mutableStateOf(listOf<NewsItem>())
    var sourcesList by mutableStateOf(listOf<SourceItem>())
    var newsFoundState by mutableStateOf(true)
    var selectedTabIndex by mutableIntStateOf(0)
    var loadingState by mutableStateOf(true)
    var messageState by mutableStateOf<String?>(null)
    private var getSourcesCall: Call<SourcesResponse>? = null
    private var getNewsCall: Call<NewsResponse>?=null



    fun getSources(category:String){
        loadingState=true
        getSourcesCall = ApiManager.sourcesService.getSources(
            category = category
        )
        getSourcesCall?.enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources
                    sources?.let {
                        sourcesList = it

                    }
                }else{
                    messageState = response.errorBody().toString()
                }
                loadingState=false

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                messageState = t.localizedMessage ?:""
                loadingState=false

            }

        })

    }


    fun getNews(sourceId: String) {
        Log.e("$$$","getNews is called")

        getNewsCall = ApiManager.newsService.getNews(
            sourceId = sourceId
        )
        getNewsCall?.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                loadingState = false
                if (response.isSuccessful) {
                    val news = response.body()?.articles
                    if (!news.isNullOrEmpty()) {
                        newsList = news
                        newsFoundState = true

                    } else {
                        newsFoundState = false
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                messageState = t.localizedMessage ?:""
                loadingState = false

            }

        })
    }




    override fun onCleared() {
        super.onCleared()
        getSourcesCall?.cancel()
        getNewsCall?.cancel()
    }
}
