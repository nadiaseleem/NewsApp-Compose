package com.example.news.fragments.newsdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsItem
import com.example.news.model.article.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel : ViewModel() {
    private var getNewsItemCall: Call<NewsResponse>? = null
    var newsItem by mutableStateOf<NewsItem?>(null)
    var messageState by mutableStateOf("")
    fun getNewsItem(title: String) {
        getNewsItemCall = ApiManager.newsService.getArticle(field = "title", title = title)
        getNewsItemCall?.enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    if (articles?.isNotEmpty() == true) {
                        articles[0].let {
                            newsItem = it
                        }
                    }
                }

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                messageState = t.localizedMessage ?: ""
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        getNewsItemCall?.cancel()

    }
}
