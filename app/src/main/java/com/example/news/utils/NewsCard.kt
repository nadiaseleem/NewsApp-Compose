package com.example.news.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.news.R
import com.example.news.model.article.NewsItem
import com.example.news.model.source.SourceItem
import com.example.news.ui.theme.Poppins
import com.example.news.ui.theme.gray
import com.example.news.ui.theme.lightGray

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(newsItem: NewsItem) {
    Column(modifier = Modifier.padding(8.dp)) {

        GlideImage(
            model = newsItem.urlToImage?:"",
            contentDescription = stringResource(R.string.news_image),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop, loading =placeholder(R.drawable.loading), transition = CrossFade
        )
        Text(
            text = newsItem.source?.name ?: "",
            fontWeight = FontWeight.Thin,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = Poppins,
            color = gray
        )
        Text(
            text = newsItem.title ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            modifier = Modifier.padding(top = 5.dp),
            fontFamily = Poppins,
            lineHeight = TextUnit(
                20f, TextUnitType.Sp
            )
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                newsItem.publishedAt ?: "",
                color = lightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewNewsItem() {
    NewsCard(
        NewsItem(
            title = "Why are football's biggest clubs starting a new \n" + "tournament?",
            source = SourceItem(name = "ABC News"),
            publishedAt = "3 hours ago"
        ),
    )
}
