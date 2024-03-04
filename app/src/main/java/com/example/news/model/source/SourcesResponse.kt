package com.example.news.model.source

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourcesResponse(

    @field:SerializedName("sources")
	val sources: List<SourceItem>? = null,

    @field:SerializedName("status")
	val status: String? = null
) : Parcelable
