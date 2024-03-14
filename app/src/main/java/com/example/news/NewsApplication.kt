package com.example.news

import android.app.Application
import com.example.news.api.ApiConstants.loadRawResources

class NewsApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        loadRawResources(this)
    }
}
