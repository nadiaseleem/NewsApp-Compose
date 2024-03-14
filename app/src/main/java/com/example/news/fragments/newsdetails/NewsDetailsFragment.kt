package com.example.news.fragments.newsdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.R
import com.example.news.activities.HomeActivity
import com.example.news.model.article.NewsItem
import com.example.news.ui.theme.Poppins
import com.example.news.ui.theme.textColor
import com.example.news.widgets.NewsCard
import com.example.news.widgets.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun NewsDetailsScreen(title:String,scope: CoroutineScope, drawerState: DrawerState,vm: NewsDetailsViewModel = viewModel()) {

    if (vm.newsItem==null) {
        LaunchedEffect(key1 = Unit) {
            vm.getNewsItem(title)
        }
    }

    if (vm.messageState.isNotEmpty()){
        AlertDialog(onDismissRequest = { vm.messageState=""}, confirmButton = {
            TextButton(onClick = {
                vm.messageState=""
            }) {
                Text(text = "Ok")
            }
        }, title = {
            Text(text = vm.messageState)
        })
    }
            Scaffold(topBar = {
                NewsTopAppBar(
                    shouldDisplaySearchIcon = false,
                    shouldDisplayMenuIcon = false,
                    titleResourceId = R.string.news, scope = scope, drawerState = drawerState
                )



            }) { paddingValues: PaddingValues ->
                vm.newsItem?.let { NewsDetailsContent(it,paddingValues) }
            }



}

@Composable
private fun NewsDetailsContent( newsItem: NewsItem,paddingValues: PaddingValues) {

    Column(modifier = Modifier
        .padding(top = paddingValues.calculateTopPadding())
        .paint(
            painterResource(
                id = R.drawable.bg_pattern
            ), contentScale = ContentScale.Crop
        )
        .verticalScroll(rememberScrollState())) {
        NewsCard(newsItem =newsItem)
        NewsDetailsCard(newsItem)
    }
}

@Composable
private fun NewsDetailsCard(newsItem: NewsItem) {
    Column(modifier= Modifier
        .padding(10.dp)
        .background(Color.White, RoundedCornerShape(10.dp))
        .padding(8.dp)){

        val context = (LocalContext.current) as HomeActivity

        Text(
            text = newsItem.content?:"",
            modifier = Modifier.padding(10.dp),
            fontFamily = Poppins,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.fillMaxHeight(.7f))

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.openWebsiteForNews(newsItem.url)
            }, verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.fillMaxWidth(.6f))
            Text(
                text = stringResource(R.string.view_full_article),
                modifier = Modifier.padding(8.dp),
                fontFamily = Poppins,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.drawable.right_arrow), contentDescription = null)
        }

    }
}



@Preview
@Composable
private fun PreviewNewsDetailsScreen() {
    NewsDetailsScreen("", rememberCoroutineScope(), rememberDrawerState(initialValue = DrawerValue.Closed),
        viewModel())
}
