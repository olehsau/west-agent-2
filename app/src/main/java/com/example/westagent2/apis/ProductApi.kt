package com.example.westagent2.apis

import android.content.Context
import android.util.Log
import com.example.westagent2.apis.RetrofitInstance.apiService
import com.example.westagent2.db.AppDatabase
import com.example.westagent2.db.dataentities.ProductResponse
import com.example.westagent2.utilities.parseXml
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

suspend fun getProductsFromServerToDatabase(context: Context,
                                            sessionId: String,
                                            onFailure: (message:String)->Unit,
                                            onSuccess: ()->Unit) {
    try {
        //val data = compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SRequestPack><SessionId>$sessionId</SessionId><Request>products</Request><Param /><scode>10387</scode></SRequestPack>")
        val requestBody = RequestBodyData(
            data = compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SRequestPack><SessionId>$sessionId</SessionId><Request>products</Request><Param /><scode>10387</scode></SRequestPack>"),
            scode = 10387
        )
        val call = apiService.getProductData(requestBody)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val rawResponse = response.body()?.string()
                if (rawResponse.isNullOrEmpty()) {
                    onFailure("Wrong username or password")
                    return
                }
                rawResponse?.let {
                    // Parse JSON
                    val jsonResponse = JSONObject(it)
                    val header = jsonResponse.getString("Header")
                    val compressedBody = jsonResponse.getString("Body")

                    // Decompress the Body
                    val decompressedBody = decodeAndDecompress(compressedBody)
                    if (decompressedBody.isNullOrEmpty()) {
                        onFailure("error")
                        return
                    }
                    val productResponse: ProductResponse =
                        parseXml(decompressedBody, ProductResponse::class.java)
                    Log.d("products response", "Parsed Products: ${productResponse.toString()}")
                    // Save parsed data into the local database
                    val db = AppDatabase.getInstance(context)
                    CoroutineScope(Dispatchers.IO).launch {
                        db.productDao().insertProducts(productResponse.products)
                        db.productDao().insertBrands(productResponse.brands)
                        db.productDao().insertGroups(productResponse.groups)
                        db.productDao().insertPriceGroups(productResponse.priceGroups)
                        db.productDao().insertUnitTypes(productResponse.unitTypes)
                        onSuccess()
                        Log.d("products response", "Data inserted into the database")
                    }

                    Log.d("products response", "Decompressed Body: $decompressedBody")
                    Log.d("products response", "Decompressed Body: ${decompressedBody.substring(50000,53000)}")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("products response", "Request failed: ${t.message}")
                onFailure("Request failed: ${t.message}")
            }
        })
    }
    catch (e: Exception){
        onFailure(e.message.toString())
    }

}