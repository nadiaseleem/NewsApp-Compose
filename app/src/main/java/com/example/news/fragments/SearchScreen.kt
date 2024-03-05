package com.example.news.fragments

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.R
import com.example.news.api.ApiConstants
import com.example.news.api.ApiManager
import com.example.news.model.article.NewsItem
import com.example.news.model.article.NewsResponse
import com.example.news.ui.theme.green
import com.example.news.ui.theme.transparent
import com.example.news.widgets.NewsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchScreen(onNewsClick:(String)->Unit) {

    var newsList by rememberSaveable {
        mutableStateOf(listOf<NewsItem>())
    }

    var newsFoundState by rememberSaveable {
        mutableStateOf(true)
    }
    var loadingState by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var getSearchedArticlesCall: Call<NewsResponse>? = null
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .paint(painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp, bottomStart =
                        30.dp
                    )
                )
                .background(green)
                .padding(horizontal = 20.dp), contentAlignment = Alignment.Center
        )
        {
            TextField(
                value = searchQuery,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                ),
                onValueChange = {
                    searchQuery = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_articles))
                },
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = stringResource(
                            id = R.string.search
                        ),
                        modifier = Modifier.clickable {
                            loadingState = true

                            getSearchedArticlesCall = ApiManager.newsService.getSearchedArticles(searchQuery=searchQuery, apiKey = ApiConstants.getApiKey(context))
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
                                    }
                                    loadingState = false
                                }

                                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                                    loadingState = false

                                    Log.e("getSearchedArticlesCall onFailure",t.localizedMessage?:"")
                                }

                            })
                        })
                }, maxLines = 1,keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    loadingState = true

                    getSearchedArticlesCall = ApiManager.newsService.getSearchedArticles(searchQuery=searchQuery, apiKey = ApiConstants.getApiKey(context))
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
                            }
                            loadingState = false
                            searchQuery=""

                        }

                        override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                            loadingState = false
                            searchQuery=""

                            Log.e("getSearchedArticlesCall onFailure",t.localizedMessage?:"")
                        }

                    })
                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    if (isFocused) {
                        Image(painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = stringResource(
                                id = R.string.close
                            ),
                            modifier =

                            Modifier.clickable {
                                if (searchQuery.isNotEmpty()) {
                                    searchQuery = ""
                                }else{
                                    Log.e("%%","${focusRequester.freeFocus()}")
                                    focusManager.clearFocus()
                                    isFocused = false
                                }

                            })
                    }
                },
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(22.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = true })
        }


        NewsList(
            newsList = newsList,
            newsFoundState = newsFoundState,
            loadingState = loadingState, onNewsClick = {onNewsClick(it)}
        )

    }
    DisposableEffect(key1 = searchQuery) {
        onDispose {
            getSearchedArticlesCall?.cancel()
        }
    }

}



@Preview(showSystemUi = true)
@Composable
private fun PreviewSearchScreen() {
    SearchScreen(){

    }
}
