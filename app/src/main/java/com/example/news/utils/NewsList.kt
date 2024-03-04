package com.example.news.utils

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.model.article.NewsItem

@Composable
fun NewsList(newsList: List<NewsItem>, newsFoundState: Boolean, loadingState: Boolean) {

    Log.e("@@@", "NewsList composition")

    if (loadingState) {

        ProgressIndicator()

    } else {
        if (newsFoundState) {
            LazyColumn(verticalArrangement = Arrangement.SpaceEvenly) {
                items(newsList.size) { position ->
                    NewsCard(newsList[position])
                }
            }
        } else {
            ArticlesNotFound()

        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun PreviewNewsList() {
    NewsList(listOf(), true,true)
}
