package com.example.news.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.R
import com.example.news.fragments.categories.CategoriesFragment
import com.example.news.fragments.news.NewsFragment
import com.example.news.fragments.newsdetails.NewsDetailsScreen
import com.example.news.fragments.search.SearchScreen
import com.example.news.fragments.settings.SettingsFragment
import com.example.news.ui.theme.NewsTheme
import com.example.news.widgets.NavigationDrawerSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle=  SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {

                NavigationDrawer()

            }
        }
    }

    fun openWebsiteForNews(url: String?) {
        url?.let {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }



}

@Composable
fun NavigationDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    ModalNavigationDrawer(drawerContent = {
        NavigationDrawerSheet(onNavigateToCategoriesClick = {
            navController.popBackStack()

            if (navController.currentDestination?.route != "categories") {
                navController.navigate("categories")
            }
            scope.launch {
                drawerState.close()
            }


        }, onNavigateToSettingsClick = {
            navController.navigate("settings")
            scope.launch {
                drawerState.close()
            }
        })
    }, drawerState = drawerState) {


        NewsAppNavigation(navController, scope, drawerState)


    }
}


@Composable
fun NewsAppNavigation(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {

    NavHost(
        navController = navController,
        startDestination = "categories"
    ) {
        composable("categories") {

            CategoriesFragment(
                scope=scope,
                drawerState=drawerState
            ) { categoryApiID, categoryName ->
                navController.navigate("news/$categoryApiID/$categoryName")
            }

        }

        composable(
            "news/{categoryApiID}/{categoryName}",
            arguments = listOf(navArgument("categoryApiID") {
                type = NavType.StringType
            }, navArgument("categoryName") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val categoryApiID =
                navBackStackEntry.arguments?.getString("categoryApiID") ?: "business"
            val categoryName =
                navBackStackEntry.arguments?.getInt("categoryName") ?: R.string.app_name

            NewsFragment(
                categoryApiID,
                categoryName,
                scope,
                drawerState,
            onNewsClick =  { title ->
                navController.navigate("newsDetails/$title")
            }, onSearchClick = {
                navController.navigate("search")
                })
        }

        composable(
            route = "newsDetails/{title}",
            arguments = listOf(navArgument("title") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->

            val title = navBackStackEntry.arguments?.getString("title") ?: ""
            NewsDetailsScreen(
                title,
                scope,
                drawerState
            )
        }

        composable(route = "search") {
            SearchScreen { title ->
                navController.navigate("newsDetails/$title")
            }
        }

        composable(route="settings"){
            SettingsFragment(scope,drawerState)
        }
    }


}


@Preview(showSystemUi = true)
@Composable
fun PreviewNewsScreen() {
    NavigationDrawer()
}
