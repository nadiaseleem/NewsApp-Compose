package com.example.news.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel

class SettingsViewModel:ViewModel() {
    var isExpanded by mutableStateOf(false)

    var selectedLanguage by mutableStateOf(getCurrentLanguage())

    private fun getCurrentLanguage():String{

        val systemLanguage = when (Locale.current.language) {
            "en" -> "English"
            "ar" -> "العربية"
            else -> "English"
        }
        var currentLanguage =
            AppCompatDelegate.getApplicationLocales()[0]?.displayLanguage ?: systemLanguage

         currentLanguage=if (currentLanguage=="Arabic")  "العربية"  else currentLanguage

        return currentLanguage
    }

}
