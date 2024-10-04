package com.example.westagent2

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import com.example.westagent2.ui.theme.WestAgent2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WestAgent2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //setBarColor(Color.Blue)
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen( modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WestAgent2Theme {
        HomeScreen()
    }
}




@Composable
fun setBarColor(color: Color) {
    // Set the color of status and navigation bars
    val window = (LocalContext.current as? Activity)?.window ?: return
    val insetsController = WindowInsetsControllerCompat(window, window.decorView)

    // Update the status bar color
    window.statusBarColor = color.toArgb()

    // Set light or dark icons based on the brightness of the color
    val isDarkTheme = color.luminance() < 0.5
    insetsController.isAppearanceLightStatusBars = !isDarkTheme
    insetsController.isAppearanceLightNavigationBars = !isDarkTheme

    // Update the navigation bar color
    window.navigationBarColor = color.toArgb()
}