package com.example.westagent2.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.westagent2.R
import com.example.westagent2.utilities.FullWidthButton
import com.example.westagent2.ui.theme.WestAgent2Theme


@Composable
fun ProductsScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.price_list),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = { /* Refresh Action */ }) {
                Icon(
                    Icons.Default.Refresh, contentDescription = "Refresh",
                )
            }
            IconButton(onClick = { /* Search Action */ }) {
                Icon(
                    Icons.Default.Search, contentDescription = "Search",
                )
            }
        }

        // Products List
        ProductItem("Laptop", "In stock: 10", "$999.99")
        ProductItem("Smartphone", "In stock: 25", "$699.99")
        ProductItem("Headphones", "In stock: 50", "$199.99")
        ProductItem("Monitor", "In stock: 8", "$249.99")
        ProductItem("Keyboard", "In stock: 30", "$79.99")

        // Bottom Buttons
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FullWidthButton(text = "Sort", modifier = Modifier.weight(1f))
            FullWidthButton(text = "Filter", modifier = Modifier.weight(1f), isPrimary = false)
        }
    }
}

@Composable
fun ProductItem(name: String, stock: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = name, color = Color(0xFF111418), fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
            Text(text = stock, color = Color(0xFF637588))
        }
        Text(text = price, color = Color(0xFF637588))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductsScreenPreview() {
    ProductsScreen()
}
