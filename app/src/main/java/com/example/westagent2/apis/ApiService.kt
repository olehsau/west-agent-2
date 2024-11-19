package com.example.westagent2.apis

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

// Request body model
data class RequestBodyData(
    val data: String?,
    val scode: Int
)

// Response body model
data class SessionResponse(
    val Header: String,
    val Body: String
)

// Retrofit service interface
interface ApiService {
    @POST("AgentService/GetSession")
    @Headers("Content-Type: application/json")
    fun getSession(@Body requestBody: RequestBodyData?): Call<ResponseBody> // Use ResponseBody for raw response

    @POST("AgentService/GetDataFile")
    @Headers("Content-Type: application/json")
    suspend fun getProductData(
        //@Field("data") data: String,
        //@Field("scode") scode: Int = 10387
        @Body requestBody: RequestBodyData?
    ): Response<ResponseBody>
}


object RetrofitInstance {
    private const val BASE_URL = "http://195.248.234.193:12987/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


@OptIn(ExperimentalEncodingApi::class)
fun decodeAndDecompress(compressedBody: String?): String? {
    return try {
        if (compressedBody != null) {
            // Decode the Base64-encoded string
            //val compressedBytes = Base64.getDecoder().decode(compressedBody)
            val compressedBytes = Base64.decode(
                compressedBody
                    .replace("-", "+")
                    .replace("_", "/")
            )

            // Decompress using GZIP
            val byteArrayInputStream = ByteArrayInputStream(compressedBytes)
            val gzipInputStream = GZIPInputStream(byteArrayInputStream)
            val decompressedBytes = gzipInputStream.readBytes()

            // Convert bytes to a String
            String(decompressedBytes)
        } else {
            null
        }
    } catch (e: Exception) {
        Log.e("MainActivity", "Decompression failed: ${e.message}")
        null
    }
}

@OptIn(ExperimentalEncodingApi::class)
fun compressAndEncode(input: String?): String? {
    return try {
        if (input != null) {
            // Compress the input string using GZIP
            val byteArrayOutputStream = ByteArrayOutputStream()
            val gzipOutputStream = GZIPOutputStream(byteArrayOutputStream)
            gzipOutputStream.write(input.toByteArray())
            gzipOutputStream.close()

            // Get the compressed bytes
            val compressedBytes = byteArrayOutputStream.toByteArray()

            // Encode the compressed bytes using Base64
            val base64Encoded = Base64.encode(compressedBytes)

            // Replace + with - and / with _ for URL-safe Base64
            base64Encoded
                .replace("+", "-")
                .replace("/", "_")
        } else {
            null
        }
    } catch (e: Exception) {
        Log.e("MainActivity", "Compression and encoding failed: ${e.message}")
        null
    }
}
