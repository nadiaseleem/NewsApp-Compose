package com.example.news.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build



fun getCurrentLanguage(context: Context):String{

    val currentAppLocales: String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales[0].displayLanguage
        } else {
            context.resources.configuration.locale.displayLanguage
        }

    return currentAppLocales

}
