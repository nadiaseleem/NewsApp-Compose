package com.example.news.fragments

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.R
import com.example.news.api.ApiConstants
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsItem
import com.example.news.model.article.NewsResponse
import com.example.news.widgets.NewsList
import com.example.news.widgets.NewsTopAppBar
import com.example.news.widgets.SourcesTabRow
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsFragment(categoryID:String,categoryName:Int, scope: CoroutineScope, drawerState: DrawerState,onNewsClick:(String)->Unit,onSearchClick:()->Unit) {
    var newsList by remember {
        mutableStateOf(listOf<NewsItem>())
    }
    var shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(true)
    }
    var shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }
    val context = LocalContext.current

var newsFoundState by rememberSaveable {
    mutableStateOf(true)
}

    var loadingState by rememberSaveable {
        mutableStateOf(true)
    }

    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = shouldDisplaySearchIcon,
            shouldDisplayMenuIcon = shouldDisplayMenuIcon,
            titleResourceId = categoryName, scope = scope, drawerState = drawerState, onSearchClick = {
                onSearchClick()
            }
        )

    }) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .paint(
                    painterResource(id = R.drawable.bg_pattern),
                    contentScale = ContentScale.Crop
                )
        ) {
            SourcesTabRow(
                category = categoryID,
                onSourceClick = { sourceId ->
                    val getNewsCall = ApiManager.newsService.getNews(
                        sourceId = sourceId,
                        apiKey = ApiConstants.getApiKey(context)
                    )
                    getNewsCall.enqueue(object : Callback<NewsResponse> {
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
                            Log.e("getNewsCall - onFailure", t.localizedMessage ?: "")
                            loadingState = false

                        }

                    })
                    getNewsCall
                })
            NewsList(newsList, newsFoundState, loadingState, onNewsClick)
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNewsFragmentScreen() {
    NewsFragment(categoryName = R.string.business, categoryID = "business", scope = rememberCoroutineScope(), drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed), onSearchClick = {}, onNewsClick = {})

}
