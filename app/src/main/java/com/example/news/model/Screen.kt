package com.example.news.model

import com.example.news.R

sealed class Screen(var route:String,val descritopn:Int) {

    data object NewsScreen : Screen(route = "news", descritopn = R.string.news)

    data object CategoriesScreen : Screen(route = "categories", descritopn = R.string.categories)
}
