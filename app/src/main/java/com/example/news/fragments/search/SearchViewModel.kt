package com.example.news.fragments.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsItem
import com.example.news.model.article.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private var getSearchedArticlesCall: Call<NewsResponse>?=null
    var newsList by mutableStateOf(listOf<NewsItem>())
    var newsFoundState by mutableStateOf(true)
    var searchQuery by mutableStateOf("")
    var isFocused by   mutableStateOf(false)
    val focusRequester =   FocusRequester()
    var loadingState by mutableStateOf(false)
    var messageState by mutableStateOf<String?>(null)
    fun getNewsFor(searchQuery: String) {
        getSearchedArticlesCall = ApiManager.newsService.getSearchedArticles(searchQuery=searchQuery)
        getSearchedArticlesCall?.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {

                if(response.isSuccessful){
                    val news = response.body()?.articles
                    if (!news.isNullOrEmpty()){
                        newsList= news
                        newsFoundState=true
                    }else{
                        newsFoundState=false
                    }
                }else{
                    messageState = response.errorBody().toString()

                }
                loadingState = false
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                loadingState = false
                messageState = t.localizedMessage ?:""

            }

        })
    }
    override fun onCleared() {
        super.onCleared()
        getSearchedArticlesCall?.cancel()
    }
}
