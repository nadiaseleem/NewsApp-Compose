package com.example.news.api

import android.content.Context
import com.example.news.R
import com.example.news.model.Category
import java.util.Properties

object ApiConstants {

    fun getApiKey(context: Context): String {
        val properties = Properties()
        val inputStream = context.resources.openRawResource(R.raw.secrets)
        properties.load(inputStream)
        return  properties.getProperty("news_api_key")
    }

}
