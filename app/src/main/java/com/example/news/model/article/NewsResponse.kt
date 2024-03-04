package com.example.news.model.article

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(

    @field:SerializedName("totalResults")
	val totalResults: Int? = null,

    @field:SerializedName("articles")
	val articles: List<NewsItem>? = null,

    @field:SerializedName("status")
	val status: String? = null
) : Parcelable
