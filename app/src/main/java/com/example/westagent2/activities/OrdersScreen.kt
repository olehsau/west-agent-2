package com.example.westagent2.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.westagent2.R
import com.example.westagent2.utilities.FullWidthButton
import com.example.westagent2.utilities.OrderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OrdersScreen(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
){
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
                IconButton(onClick = { coroutineScope.launch { drawerState.open() }}) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
                Text(
                    text = stringResource(R.string.orders_screen_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /* Refresh Action */ }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = { /* Search Action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            // Filter Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(stringResource(R.string.in_progress), OrderStatus.IN_PROGRESS)
                FilterButton(stringResource(R.string.ready), OrderStatus.READY)
                FilterButton(stringResource(R.string.sent), OrderStatus.SENT)
            }

            // Orders List
            OrderItem("Sophia Williams", "3 items · $30.50", OrderStatus.IN_PROGRESS)
            OrderItem("Noah Johnson", "2 items · $20.50", OrderStatus.READY)
            OrderItem("Olivia Davis", "5 items · $50.50", OrderStatus.SENT)
            OrderItem("Elijah Miller", "4 items · $40.50", OrderStatus.IN_PROGRESS)
            OrderItem("Liam Brown", "3 items · $20.50", OrderStatus.READY)
            OrderItem("Emma Garcia", "2 items · $15.50", OrderStatus.SENT)

            // Bottom Buttons
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FullWidthButton(
                    stringResource(R.string.sort),
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                FullWidthButton(
                    stringResource(R.string.filter),
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    isPrimary = false
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthButton(stringResource(R.string.new_order), onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthButton(stringResource(R.string.send_all_orders), onClick = {})
        }
    }
}

@Composable
fun FilterButton(text: String, status: OrderStatus) {
    Button(
        onClick = { /* TODO: Handle filter action */ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F2F4)),
        modifier = Modifier.height(32.dp)
    ) {
        Text(text = text, color = Color(0xFF111418), style = MaterialTheme.typography.bodySmall)
        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
    }
}

@Composable
fun OrderItem(name: String, details: String, status: OrderStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = name, color = Color(0xFF111418), fontWeight = FontWeight.Medium)
            Text(text = details, color = Color(0xFF637588))
        }
        Column(horizontalAlignment = AbsoluteAlignment.Right) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        when (status) {
                            OrderStatus.IN_PROGRESS -> Color(0xFF888C91)
                            OrderStatus.READY -> Color(0xFF4AA9FF)
                            OrderStatus.SENT -> Color(0xFF078838)
                        }, shape = CircleShape
                    )
            )
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreenPreview() {
    OrdersScreen(drawerState = DrawerState(DrawerValue.Closed), coroutineScope = rememberCoroutineScope())
}

