package com.example.news.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.R
import com.example.news.fragments.CategoriesFragment
import com.example.news.fragments.NewsFragment
import com.example.news.model.Screen
import com.example.news.ui.theme.NewsTheme
import com.example.news.utils.NavigationDrawerSheet
import com.example.news.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                NavigationDrawer()
            }
        }
    }

}

@Composable
fun NavigationDrawer() {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    ModalNavigationDrawer(drawerContent = {
        NavigationDrawerSheet(onNavigateToCategoriesClick = {
            navController.popBackStack()

            if (navController.currentDestination?.route != Screen.CategoriesScreen.route) {
                navController.navigate(Screen.CategoriesScreen.route)
            }
            scope.launch {
                drawerState.close()
            }


        }, onNavigateToSettingsClick = {
            scope.launch {
                drawerState.close()
            }
        })
    }, drawerState = drawerState) {
        HomeLayout(scope, drawerState, navController)
    }
}


@Composable
fun HomeLayout(scope: CoroutineScope, drawerState: DrawerState, navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    var shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }
    shouldDisplaySearchIcon = (currentRoute != Screen.CategoriesScreen.route)

    var titleResourceId by rememberSaveable {
        mutableIntStateOf(R.string.news_app)
    }
    Scaffold(topBar = {
        NewsTopAppBar(shouldDisplaySearchIcon= shouldDisplaySearchIcon,shouldDisplayMenuIcon= shouldDisplayMenuIcon,titleResourceId = titleResourceId) {
            scope.launch {
                drawerState.open()
            }
        }

    }) { paddingValues: PaddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.CategoriesScreen.route,
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            composable(Screen.CategoriesScreen.route) {
                titleResourceId = R.string.news_app

                CategoriesFragment { categoryApiID, categoryName ->
                    navController.navigate("${Screen.NewsScreen.route}/$categoryApiID/$categoryName")
                }

            }

            composable(
                "${Screen.NewsScreen.route}/{categoryApiID}/{categoryName}",
                arguments = listOf(navArgument("categoryApiID") {
                    type = NavType.StringType
                }, navArgument("categoryName") {
                    type = NavType.IntType
                })
            ) { navBackStackEntry ->


                val categoryApiID =
                    navBackStackEntry.arguments?.getString("categoryApiID") ?: "business"
                val categoryName =
                    navBackStackEntry.arguments?.getInt("categoryName") ?: R.string.news_app
                titleResourceId = categoryName

                NewsFragment(categoryApiID)
            }
        }


    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewNewsScreen() {
    NavigationDrawer()
}
