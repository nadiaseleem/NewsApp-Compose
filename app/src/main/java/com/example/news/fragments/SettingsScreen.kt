package com.example.news.fragments

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.example.news.R
import com.example.news.activities.HomeActivity
import com.example.news.ui.theme.Poppins
import com.example.news.ui.theme.darkGray
import com.example.news.ui.theme.green
import com.example.news.widgets.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingsFragment(scope: CoroutineScope, drawerState: DrawerState) {

    var shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }


    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = shouldDisplaySearchIcon,
            shouldDisplayMenuIcon = shouldDisplayMenuIcon,
            titleResourceId = R.string.settings,
            scope = scope,
            drawerState = drawerState
        )
    }) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .paint(
                    painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop
                )
                .padding(30.dp)
        ) {

            Text(
                text = stringResource(R.string.language),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = Poppins,
                color = darkGray
            )

            Spacer(Modifier.height(10.dp))
            LanguageDropDownMenu()
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropDownMenu() {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val activity = (LocalContext.current) as HomeActivity

    val systemLanguage = when (Locale.current.language) {
        "en" -> stringResource(id = R.string.english)
        "ar" -> stringResource(id = R.string.arabic)
        else -> stringResource(id = R.string.english)
    }
    var currentLanguage =
        AppCompatDelegate.getApplicationLocales()[0]?.displayLanguage ?: systemLanguage

    currentLanguage=if (currentLanguage=="Arabic") stringResource(id = R.string.arabic) else currentLanguage


    var selectedLanguage by rememberSaveable {
        mutableStateOf(currentLanguage)
    }
    Log.e("%%currentLanguage ",currentLanguage)
    Log.e("%%selectedLanguage ",selectedLanguage)


    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedLanguage, onValueChange = {

            }, readOnly = true,

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            }, colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = green,
                unfocusedBorderColor = Color.Black,
                focusedTextColor = green,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ), modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {

            DropdownMenuItem(text = {
                Text(text = stringResource(id = R.string.english))
            }, onClick = {
                selectedLanguage = "English"
                isExpanded = false
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                activity.finish()
                activity.startActivity(activity.intent)
            }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)

            DropdownMenuItem(text = {
                Text(text = stringResource(id = R.string.arabic))
            }, onClick = {
                selectedLanguage = "العربية"
                isExpanded = false
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                activity.finish()
                activity.startActivity(activity.intent)

            }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)

        }

    }

}


@Preview
@Composable
private fun PreviewSettingsFragment() {
    SettingsFragment(
        rememberCoroutineScope(),
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
}
