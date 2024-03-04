package com.example.news.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.R
import com.example.news.ui.theme.green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(titleResourceId:Int, shouldDisplaySearchIcon:Boolean,shouldDisplayMenuIcon:Boolean, onSideMenuClick: () -> Unit = {}) {



    TopAppBar(title = {
        Text(
            text = stringResource(titleResourceId),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, actions = {
        if (shouldDisplaySearchIcon)
        AppBarIconButton(R.drawable.ic_search)
        else
            Spacer(modifier = Modifier
                .padding(10.dp)
                .size(25.dp) )
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = green),
        navigationIcon = {
            if (shouldDisplayMenuIcon)
                AppBarIconButton(R.drawable.ic_feather_menu, onClick = onSideMenuClick)
            else
                Spacer(modifier = Modifier
                    .padding(10.dp)
                    .size(25.dp) )
        },
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEnd = 30.dp, bottomStart =
                30.dp
            )
        )
    )
}

@Composable
private fun AppBarIconButton(icon: Int, onClick: () -> Unit = {}) {
    Image(
        painter = painterResource(icon),
        contentDescription = stringResource(R.string.search),
        modifier = Modifier
            .padding(10.dp)
            .size(25.dp)
            .clickable(onClick = onClick)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewNewsTopAppBar() {
    NewsTopAppBar(R.string.news_app,true,true)
}
