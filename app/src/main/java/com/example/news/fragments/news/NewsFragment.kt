package com.example.news.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.R
import com.example.news.widgets.NewsList
import com.example.news.widgets.NewsTopAppBar
import com.example.news.widgets.SourcesTabRow
import kotlinx.coroutines.CoroutineScope


@Composable
fun NewsFragment(
    categoryID: String,
    categoryName: Int,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onNewsClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    vm: NewsViewModel = viewModel()
) {

    if (!vm.messageState.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = { vm.messageState = "" },
            confirmButton = {
                TextButton(onClick = {
                    vm.messageState = ""
                }) {
                    Text(text = "Ok")
                }
            },
            title = {
                Text(text = vm.messageState!!)
            })
    }

    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = true,
            shouldDisplayMenuIcon = true,
            titleResourceId = categoryName,
            scope = scope,
            drawerState = drawerState,
            onSearchClick = {
                onSearchClick()
            }
        )

    }) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .paint(
                    painterResource(id = R.drawable.bg_pattern),
                    contentScale = ContentScale.Crop
                )
        ) {
            SourcesTabRow(
                category = categoryID, vm
            )
            NewsList(vm.newsList, vm.newsFoundState, vm.loadingState, onNewsClick)
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNewsFragmentScreen() {
    NewsFragment(categoryName = R.string.business,
        categoryID = "business",
        scope = rememberCoroutineScope(),
        drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed
        ),
        onSearchClick = {},
        onNewsClick = {},
        vm = viewModel()
    )

}
