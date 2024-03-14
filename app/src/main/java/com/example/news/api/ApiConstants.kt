package com.example.news.api

import android.content.Context
import com.example.news.R
import java.util.Properties

object ApiConstants {
   private val properties = Properties()

    fun getApiKey(): String {
        return  properties.getProperty("news_api_key")
    }
    fun loadRawResources(context: Context){
        val inputStream = context.resources.openRawResource(R.raw.secrets)
        properties.load(inputStream)
    }

}
