package com.example.westagent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.westagent2.activities.DrawerMenuScreen
import com.example.westagent2.db.AppDatabase
import com.example.westagent2.ui.theme.WestAgent2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        setContent {
            WestAgent2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DrawerMenuScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }





    }


}