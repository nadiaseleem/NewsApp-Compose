package com.example.news.fragments.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.R
import com.example.news.ui.theme.green
import com.example.news.ui.theme.transparent
import com.example.news.widgets.NewsList

@Composable
fun SearchScreen(vm: SearchViewModel =viewModel(), onNewsClick:(String)->Unit) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .paint(painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp, bottomStart =
                        30.dp
                    )
                )
                .background(green)
                .padding(horizontal = 20.dp), contentAlignment = Alignment.Center
        )
        {
            TextField(
                value = vm.searchQuery,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                ),
                onValueChange = {
                    vm.searchQuery = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_articles))
                },
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = stringResource(
                            id = R.string.search
                        ),
                        modifier = Modifier.clickable {
                            vm.loadingState = true
                            vm.getNewsFor(vm.searchQuery)

                        })
                }, maxLines = 1, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    vm.loadingState = true
                    vm.getNewsFor(vm.searchQuery)

                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    if (vm.isFocused) {
                        Image(painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = stringResource(
                                id = R.string.close
                            ),
                            modifier =

                            Modifier.clickable {
                                if (vm.searchQuery.isNotEmpty()) {
                                    vm.searchQuery = ""
                                } else {
                                    focusManager.clearFocus()
                                    vm.isFocused = false
                                }

                            })
                    }
                },
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(22.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(vm.focusRequester)
                    .onFocusChanged { vm.isFocused = true })
        }


        if (!vm.messageState.isNullOrEmpty()) {
            AlertDialog(onDismissRequest = { vm.messageState = "" }, confirmButton = {
                TextButton(onClick = {
                    vm.messageState = ""
                }) {
                    Text(text = "Ok")
                }
            }, title = {
                Text(text = vm.messageState!!)
            })
        }


            NewsList(
                newsList = vm.newsList,
                newsFoundState = vm.newsFoundState,
                loadingState = vm.loadingState, onNewsClick = { onNewsClick(it) }
            )


    }

}



@Preview(showSystemUi = true)
@Composable
private fun PreviewSearchScreen() {
    SearchScreen(vm=viewModel()){

    }
}
