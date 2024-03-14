package com.example.news.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.R
import com.example.news.activities.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle=  SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle= SystemBarStyle.light(   Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier=Modifier.fillMaxSize().navigationBarsPadding()){

                 SplashScreen()

                viewModel.delegate = OnSplashNavigations {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }, 2000)
                }

               viewModel.navigateToHomeActivity()



            }


        }
    }
}


@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.bg_pattern), contentScale = ContentScale.FillBounds),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth(.6f)
                .fillMaxHeight(.5f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.route),
            contentDescription = "Route",
            Modifier.fillMaxWidth(.5f),
            contentScale = ContentScale.Crop
        )

    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}
