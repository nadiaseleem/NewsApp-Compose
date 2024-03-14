package com.example.news.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.news.fragments.news.NewsViewModel
import com.example.news.ui.theme.green


@Composable
fun SourcesTabRow(category: String, vm: NewsViewModel) {


    val context = LocalContext.current


    if (vm.sourcesList.isEmpty()) {
        LaunchedEffect(key1 = Unit) {//- doesn't cancel async task made with retrofit calls, - doesn't survive configuration changes, + launched only when first created and when key changes
            vm.getSources(category)

        }
    }







    ScrollableTabRow(
        selectedTabIndex = vm.selectedTabIndex,
        divider = {},
        indicator = {},
        edgePadding = 8.dp
    ) {

        vm.sourcesList.forEachIndexed { index, sourceItem ->

            Tab(
                selected = index == vm.selectedTabIndex,
                onClick = {
                    vm.selectedTabIndex = index
                    vm.getNews(sourceItem.id ?: "")
                },
                selectedContentColor = if (vm.selectedTabIndex == index) Color.White else green,
                modifier =
                if (vm.selectedTabIndex == index)
                    Modifier
                        .padding(5.dp)
                        .background(green, CircleShape)
                else Modifier
                    .padding(5.dp)
                    .border(width = 1.dp, green, shape = CircleShape)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = sourceItem.name ?: "", modifier = Modifier.padding(8.dp))
            }
        }

    }

}
