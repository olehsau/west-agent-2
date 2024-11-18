package com.example.westagent2.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@Composable
fun DrawerMenuScreen(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Control drawer state
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    // Observing current back stack entry to get the active route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                activeRoute = currentRoute,  // Pass the active route to DrawerContent
                onMenuItemClick = { destination ->
                    // Handle drawer item clicks, close drawer, and navigate
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(destination) {
                        launchSingleTop = true
                    }
                }
            )
        },
        content = {
            NavHost(navController = navController, startDestination = "orders") {
                composable("home") {
                    OrdersScreen(drawerState = drawerState,
                    coroutineScope = coroutineScope)
                }
                composable("orders") {
                    OrdersScreen(drawerState = drawerState,
                        coroutineScope = coroutineScope)
                }
                composable("clients") {
                    OrdersScreen(drawerState = drawerState,
                        coroutineScope = coroutineScope)
                }
                composable("products") {
                    ProductsScreen(drawerState = drawerState,
                        coroutineScope = coroutineScope)
                }
                composable("account") {
                    AccountScreen(drawerState = drawerState,
                        navController = navController)
                }
                composable("login") {
                    LoginScreen(drawerState = drawerState,
                        navController = navController)
                }
            }
        }
    )
}

@Composable
fun DrawerContent(activeRoute: String?, onMenuItemClick: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .widthIn(100.dp, 330.dp)
            .background(Color(0xFFFFFFFF))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DrawerMenuItem(
                icon = Icons.Default.Home,
                label = "Home",
                onClick = { onMenuItemClick("home") },
                isActive = activeRoute.equals("home")
            )
            DrawerMenuItem(
                icon = Icons.Default.ShoppingCart,
                label = "Orders",
                onClick = { onMenuItemClick("orders") },
                isActive = activeRoute.equals("orders")
            )
            DrawerMenuItem(
                icon = Icons.Default.Person,
                label = "Clients",
                onClick = { onMenuItemClick("clients") },
                isActive = activeRoute.equals("clients")
            )
            DrawerMenuItem(
                icon = Icons.Default.ShoppingCart,
                label = "Products",
                onClick = { onMenuItemClick("products") },
                isActive = activeRoute.equals("products")
            )
            DrawerMenuItem(
                icon = Icons.Default.AccountCircle,
                label = "Account",
                onClick = { onMenuItemClick("account") },
                isActive = activeRoute.equals("account")
            )
        }
    }
}

@Composable
fun DrawerMenuItem(icon: ImageVector, label: String, onClick: () -> Unit, isActive: Boolean) {
    val backgroundColor = if (isActive) Color(0xFFF0F2F4) else Color.White
    Box(
        modifier = Modifier.clickable(onClick = onClick),
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp)
                .background(backgroundColor, shape = CircleShape)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF111418))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                color = Color(0xFF111418),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
