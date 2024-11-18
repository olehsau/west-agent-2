package com.example.westagent2.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.westagent2.R
import com.example.westagent2.utilities.FullWidthButton
import com.example.westagent2.utilities.clearSessionIdLocally
import com.example.westagent2.utilities.getSessionIdLocally
import com.example.westagent2.utilities.getUsernameLocally
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    drawerState: DrawerState,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sessionId = getSessionIdLocally(context)
    var showLogoutDialog by remember { mutableStateOf(false) } // State for showing the dialog

    LaunchedEffect(sessionId) {
        if (sessionId == null) {
            navController.navigate("login") { launchSingleTop = true }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
                Text(
                    text = stringResource(R.string.account_screen_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /* Refresh Action */ }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Information
            Text(text = "Інформація про агента", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            UserInfoItem("ID", getUsernameLocally(context)) // Replace with actual values
            UserInfoItem("Name", "Антипов Володимир 0678678388") // Replace with actual values

            Spacer(modifier = Modifier.height(16.dp))

            // Order Report
            Text(text = "Статистика", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            UserInfoItem("Number of Orders", "10")
            UserInfoItem("Total Price", "$500.00")
            UserInfoItem("Total Discount", "$50.00")
            UserInfoItem("Active Tradepoints", "5")
            UserInfoItem("Date", "2023-10-17") // Placeholder

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            FullWidthButton(
                text = stringResource(R.string.logout),
                onClick = {
                    showLogoutDialog = true // Show dialog when Logout button is clicked
                }
            )

            // Logout Confirmation Dialog
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text(stringResource(R.string.logout_confirmation_title)) },
                    text = { Text(stringResource(R.string.logout_confirmation_text)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                clearSessionIdLocally(context)
                                navController.navigate("login") { launchSingleTop = true }
                                showLogoutDialog = false // Dismiss dialog after logout
                            }
                        ) {
                            Text(stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun UserInfoItem(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color(0xFF111418), fontWeight = FontWeight.Medium)
        Text(text = value ?: "-", color = Color(0xFF637588))
    }
}
