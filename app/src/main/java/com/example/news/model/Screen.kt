package com.example.news.model

sealed class Screen(var route:String) {

    data object NewsScreen : Screen(route = "news")

    data object CategoriesScreen : Screen(route = "categories")

    data object NewsDetailsScreen : Screen(route = "newsDetails")


}
