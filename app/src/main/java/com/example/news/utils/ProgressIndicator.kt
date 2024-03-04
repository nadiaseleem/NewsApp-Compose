package com.example.news.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.ui.theme.green

@Composable
fun ProgressIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.material3.CircularProgressIndicator(
            color = green,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showSystemUi=true)
@Composable

private fun PreviewProgressIndicator() {
    ProgressIndicator()
}
