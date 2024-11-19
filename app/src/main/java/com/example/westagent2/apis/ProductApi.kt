package com.example.westagent2.apis

import android.content.Context
import android.util.Log
import com.example.westagent2.apis.RetrofitInstance.apiService
import com.example.westagent2.db.AppDatabase
import com.example.westagent2.db.dataentities.ProductResponse
import com.example.westagent2.utilities.parseXml

suspend fun getProductsFromServerToDatabase(context: Context,
                                            sessionId: String,
                                            onError: ()->Unit,
                                            onSuccess: ()->Unit) {
    try {
        //val data = compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SRequestPack><SessionId>$sessionId</SessionId><Request>products</Request><Param /><scode>10387</scode></SRequestPack>")
        val requestBody = RequestBodyData(
            data = compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SRequestPack><SessionId>$sessionId</SessionId><Request>products</Request><Param /><scode>10387</scode></SRequestPack>"),
            scode = 10387
        )
        val response = apiService.getProductData(requestBody)
        Log.d("products","${response.body()?.string()}")
        Log.d("products","${response.toString()}")
        if (response.isSuccessful) {
            val productResponse: ProductResponse = parseXml(response.body()?.string() ?: "", ProductResponse::class.java)
            // Save parsed data into the local database
            val db = AppDatabase.getInstance(context)
            db.productDao().insertProducts(productResponse.products)
            db.productDao().insertBrands(productResponse.brands)
            db.productDao().insertGroups(productResponse.groups)
            db.productDao().insertPriceGroups(productResponse.priceGroups)
            db.productDao().insertUnitTypes(productResponse.unitTypes)
            onSuccess()
        }
        else{
            onError()
        }
    } catch (e: Exception) {
        onError()
    }
}