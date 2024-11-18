package com.example.westagent2.apis

import android.util.Log
import com.example.westagent2.apis.RetrofitInstance.apiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import org.w3c.dom.Document
import org.xml.sax.InputSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

/**
 * performs login request and executes onLoginSuccess with sessionId as a parameter.
 * DOES NOT SAVE SESSIONID LOCALLY, IT MUST BE SAVED IN onLoginSuccess as callback !!!
 */
fun login(
    username: String?,
    password: String?,
    onLoginSuccess: (sessionId: String) -> Unit,
    onLoginFailure: (errorMessage: String) -> Unit,
    onFinish: () -> Unit
) {

    if(username.isNullOrEmpty() || password.isNullOrEmpty()){
        onLoginFailure("Username and password can not be empty")
        onFinish()
        return
    }

    // Add logging interceptor for debugging (optional)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Prepare data
    val requestBody = RequestBodyData(
        //data = "H4sIAAAAAAAAAH2MMQ7CMAxFr5LNEySFpUhuqi5IbJWAA1iJQZFaB9UBwe2Jys70_xvew_49T-bFi6YsHTRbB4Yl5Jjk3sH1cty0YLSQRJqycAcfVjC9x_PwLCwl3VKgUt2RFpq51JDHU_S7wx5tXRxJ1YfHyutHrXX2jXOuRfsDtH9yX5xrmuOjAAAA",
        data = compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SAutentificationParameters><Id>$username</Id><Pass>$password</Pass><scode>10008</scode></SAutentificationParameters>"),
        scode = 10019
    )
    compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SAutentificationParameters><Id>$username</Id><Pass>$password</Pass><scode>10008</scode></SAutentificationParameters>")?.let {
        Log.d("test",
            it
        )
    }

    // Make the HTTP POST request
    val call = apiService.getSession(requestBody)

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {
            val rawResponse = response.body()?.string()
            if(rawResponse.isNullOrEmpty()){
                onLoginFailure("Wrong username or password")
                onFinish()
                return
            }
            rawResponse?.let {
                // Parse JSON
                val jsonResponse = JSONObject(it)
                val header = jsonResponse.getString("Header")
                val compressedBody = jsonResponse.getString("Body")


                // Decompress the Body
                val decompressedBody = decodeAndDecompress(compressedBody)
                val ssid = getIdFromXml(decompressedBody)
                if (ssid == null
                    || getUserFromXml(decompressedBody).equals("-1")) {
                    onLoginFailure("Wrong username or password")
                } else {
                    onLoginSuccess(ssid)
                }
                Log.d("Session response", "Decompressed Body: $decompressedBody")
            }
            onFinish()
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("Session response", "Request failed: ${t.message}")
            onLoginFailure("Request failed: ${t.message}")
            onFinish()
        }
    })

}

fun getIdFromXml(decompressedBody: String?): String? {
    // Return null if input is null
    if (decompressedBody == null) return null

    // Create a DocumentBuilder
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()

    // Parse the decompressedBody string into a Document
    val inputSource = InputSource(StringReader(decompressedBody))
    val doc: Document = builder.parse(inputSource)

    // Get the <Id> element
    val idElement = doc.getElementsByTagName("Id").item(0)

    // Check if the element exists and has non-empty text content
    val idTextContent = idElement?.textContent
    return if (idTextContent.isNullOrBlank()) null else idTextContent
}



fun getUserFromXml(decompressedBody: String?): String? {
    // Return null if input is null
    if (decompressedBody == null) return null

    // Create a DocumentBuilder
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()

    // Parse the decompressedBody string into a Document
    val inputSource = InputSource(StringReader(decompressedBody))
    val doc: Document = builder.parse(inputSource)

    // Get the <User> element
    val userElement = doc.getElementsByTagName("User").item(0)

    // Check if the element exists and has non-empty text content
    val userTextContent = userElement?.textContent
    return if (userTextContent.isNullOrBlank()) null else userTextContent
}
