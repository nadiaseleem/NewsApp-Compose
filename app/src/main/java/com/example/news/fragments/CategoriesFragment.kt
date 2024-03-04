package com.example.news.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.model.Category
import com.example.news.model.categoriesList
import com.example.news.ui.theme.Exo
import com.example.news.ui.theme.Poppins
import com.example.news.ui.theme.textColor

@Composable
fun CategoriesFragment(onCategoryClick:(String,Int)->Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.pick_your_category_of_interest),
            fontSize = 22.sp, fontFamily = Poppins, color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start=24.dp,top=24.dp,bottom=16.dp)
        )
        CategoriesList(onCategoryClick= {id,name->
            onCategoryClick(id,name)})

    }

}

@Composable
private fun CategoriesList(onCategoryClick:(String,Int)->Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),modifier=Modifier.padding(horizontal = 10.dp)) {
        items(categoriesList.size) { index ->
            CategoryItem(categoriesList[index],onCategoryClick={id,name->onCategoryClick(id,name)})
        }
    }
}

@Composable
fun CategoryItem(category: Category,onCategoryClick:(String,Int)->Unit) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.padding(8.dp).clickable { onCategoryClick(category.apiID,category.name) }) {
        Image(painter = painterResource(id = category.image), contentDescription = category.apiID)
        Text(text = stringResource(id = category.name), color = Color.White, fontSize = 22.sp , modifier=Modifier.padding(bottom = 20.dp),fontFamily=Exo)
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCategoryItem() {
    CategoryItem(categoriesList[0]){id,name->

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoriesList() {
    CategoriesList{id,name->

    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewCategoriesFragment() {
    CategoriesFragment { id, name ->

    }
}
