package com.example.news.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.news.api.ApiConstants
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsResponse
import com.example.news.model.source.SourceItem
import com.example.news.model.source.SourcesResponse
import com.example.news.ui.theme.green
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SourcesTabRow(category: String, modifier: Modifier = Modifier,onSourceClick:(String)->Call<NewsResponse>) {
    var sourcesList by rememberSaveable {
        mutableStateOf(listOf<SourceItem>())
    }


    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Log.e("@@@","SourcesTabRow composition")


    val context = LocalContext.current
    var getSourcesCall: Call<SourcesResponse>? = null
    var getNewsCall: Call<NewsResponse>?=null

    LaunchedEffect(key1 = Unit) {//- doesn't cancel async task made with retrofit calls, - doesn't survive configuration changes, + launched only when first created and when key changes

        getSourcesCall = ApiManager.sourcesService.getSources(
            category = category,
            apiKey = ApiConstants.getApiKey(context)
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
                        getNewsCall =onSourceClick(sourcesList[selectedTabIndex].id?:"")

                    }
                }

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Log.e("MainActivity", t.localizedMessage ?: "")
            }

        })
    }



    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = {},
        edgePadding = 8.dp,
        modifier = modifier
    ) {

        sourcesList.forEachIndexed { index, sourceItem ->

            Tab(
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                    getNewsCall =onSourceClick.invoke(sourceItem.id?:"")
                },
                selectedContentColor = if (selectedTabIndex == index) Color.White else green,
                modifier =
                if (selectedTabIndex == index)
                    Modifier
                        .padding(5.dp)
                        .background(green, CircleShape)
                else Modifier
                    .padding(5.dp)
                    .border(width = 1.dp, green, shape = CircleShape)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = sourceItem.name ?: "", modifier = Modifier.padding(8.dp))
            }
        }

    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            getSourcesCall?.cancel()
            getNewsCall?.cancel()
            Log.e("@@@","Disposed")
        }
    }
}
