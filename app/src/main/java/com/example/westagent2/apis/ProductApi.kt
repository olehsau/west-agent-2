package com.example.westagent2.apis

import android.content.Context
import android.util.Log
import com.example.westagent2.R
import com.example.westagent2.apis.RetrofitInstance.apiService
import com.example.westagent2.db.AppDatabase
import com.example.westagent2.db.dataentities.Product
import org.json.JSONObject
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

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
        val response = apiService.getProductData(requestBody)

        if (response.isSuccessful) {
            val rawResponse = response.body()?.string()
            if (rawResponse.isNullOrEmpty()) {
                onFailure(context.getString(R.string.not_logged_in_error_message))
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
                    onFailure(context.getString(R.string.empty_response_error_message))
                    return
                }

                val document = parseXmlToDocument(decompressedBody)

                val productsNodeList = document.getElementsByTagName("ap")
                val brandsAinsNode = document.getElementsByTagName("Brands").item(0)
                val brandsNodeList = brandsAinsNode.childNodes
                val groupsAinsNode = document.getElementsByTagName("Groups").item(0)
                val groupsNodeList = groupsAinsNode.childNodes
                val priceGroupsAinsNode = document.getElementsByTagName("PriceGroups").item(0)
                val priceGroupsNodeList = priceGroupsAinsNode.childNodes
                val unitTypesAinsNode = document.getElementsByTagName("UnitTypes").item(0)
                val unitTypesNodeList = unitTypesAinsNode.childNodes

                val productsList = mutableListOf<Product>()
                val db = AppDatabase.getInstance(context)

                for (i in 0 until productsNodeList.length) {
                    val productNode = productsNodeList.item(i)
                    if (productNode.nodeType == Node.ELEMENT_NODE) {
                        val element = productNode as Element
                        // Extract product fields
                        val id = element.getElementsByTagName("a").item(0).textContent.toInt()
                        val groupId =
                            element.getElementsByTagName("c").item(0)?.textContent?.toIntOrNull()
                        val brandId =
                            element.getElementsByTagName("d").item(0)?.textContent?.toIntOrNull()
                        val priceGroup =
                            element.getElementsByTagName("e").item(0)?.textContent?.toIntOrNull()
                        val name = element.getElementsByTagName("b").item(0).textContent
                        val price = element.getElementsByTagName("f").item(0).textContent.toDouble()
                        val baseUnit = element.getElementsByTagName("g").item(0).textContent.toInt()
                        val caseUnit =
                            element.getElementsByTagName("h").item(0)?.textContent?.toIntOrNull()
                                ?: 0
                        val caseSize =
                            element.getElementsByTagName("i").item(0)?.textContent?.toFloatOrNull()
                                ?: 0f
                        val stock =
                            element.getElementsByTagName("j").item(0)?.textContent?.toDoubleOrNull()
                                ?: 0.0
                        val color =
                            element.getElementsByTagName("k").item(0)?.textContent?.toIntOrNull()
                                ?: 0
                        val status =
                            element.getElementsByTagName("l").item(0)?.textContent?.toIntOrNull()
                                ?: 0
                        val barcode =
                            element.getElementsByTagName("m").item(0)?.textContent?.ifBlank { null }

                        // todo all this for brands, groups, etc.

                        // Create Product object and add to list
                        productsList.add(
                            Product(
                                id,
                                groupId,
                                brandId,
                                priceGroup,
                                name,
                                price,
                                baseUnit,
                                caseUnit,
                                caseSize,
                                stock,
                                color,
                                status,
                                barcode
                            )
                        )
                    } else {
                        onFailure("Error: ${response.code()} ${response.message()}")
                    }
                }
                db.productDao().insertProducts(productsList)
                onSuccess()
            }
        }else{
            Log.e("products response", "Request failed: ${response.message()}")
            onFailure("Request failed: ${response.message()}")
        }

    }
    catch (e: Exception){
        onFailure(e.message.toString())
    }

}

fun parseXmlToDocument(xmlData: String): Document {
    val factory = DocumentBuilderFactory.newInstance()
    factory.isNamespaceAware = true // Handle namespaces in XML
    val builder = factory.newDocumentBuilder()
    return builder.parse(InputSource(StringReader(xmlData)))
}