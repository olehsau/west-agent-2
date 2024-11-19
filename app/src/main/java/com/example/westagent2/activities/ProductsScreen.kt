package com.example.westagent2.activities

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.westagent2.R
import com.example.westagent2.apis.getProductsFromServerToDatabase
import com.example.westagent2.db.AppDatabase
import com.example.westagent2.db.dataentities.Product
import com.example.westagent2.utilities.FullWidthButton
import com.example.westagent2.utilities.getSessionIdLocally
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ProductsScreen(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
){
    val context = LocalContext.current
    var productsList = remember { mutableStateListOf<Product>() }
    var error = remember { mutableStateOf(false) }
    var errorMessage = remember{ mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

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
                text = stringResource(R.string.price_list),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = {
                coroutineScope.launch {
                    getProductsFromServerToDatabase(
                        context,
                        getSessionIdLocally(context) ?: "",
                        onFailure = {message ->
                            error.value = true
                            errorMessage.value = message
                        },
                        onSuccess = {
                            error.value = false
                            // Using a coroutine to perform the database operation on the IO thread
                            CoroutineScope(Dispatchers.IO).launch {
                                val products = AppDatabase.getInstance(context).productDao().getAllProducts()
                                // Using Dispatchers.Main to update the UI on the main thread
                                withContext(Dispatchers.Main) {
                                    productsList.clear()
                                    productsList.addAll(products)
                                }
                            }
                        }
                    )
                }
            }) {
                Icon(
                    Icons.Default.Refresh, contentDescription = "Refresh",
                )
            }
            IconButton(onClick = {
                //temp
                CoroutineScope(Dispatchers.IO).launch {
                    var products = AppDatabase.getInstance(context).productDao().getAllProducts()
                    withContext(Dispatchers.Main) {
                        Log.d("product", products.get(0).toString())
                        Log.d("product", products.get(1).toString())
                        Log.d("product", products.get(2).toString())
                        Log.d("product", products.get(3).name)
                    }
                }
            }) {
                Icon(
                    Icons.Default.Search, contentDescription = "Search",
                )
            }
        }
        if(error.value==true) {
            Text(
                text = "Помилка завантаження нових даних з сервера:\n$errorMessage",
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Products List
//        ProductItem("Laptop", "In stock: 10", "$999.99")
//        ProductItem("Smartphone", "In stock: 25", "$699.99")
//        ProductItem("Headphones", "In stock: 50", "$199.99")
//        ProductItem("Monitor", "In stock: 8", "$249.99")
//        ProductItem("Keyboard", "In stock: 30", "$79.99")
        productsList.forEach { product -> run {
            ProductItem(product.name, "In stock: unknown", "${product.price}")
        }
        }

        // Bottom Buttons
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FullWidthButton(text = "Сортувати", onClick = {}, modifier = Modifier.weight(1f))
            FullWidthButton(text = "Фільтрувати", onClick = {}, modifier = Modifier.weight(1f), isPrimary = false)
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
    ProductsScreen(drawerState = DrawerState(DrawerValue.Closed), coroutineScope = rememberCoroutineScope())
}
