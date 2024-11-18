package com.example.westagent2.apis


suspend fun getProductsFromServer(
    sessionId : String?,
    onSuccess: () -> Unit,
    onFailure: (errorMessage: String) -> Unit,
    onFinish: () -> Unit
) {/*
        val data =
            compressAndEncode("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><SRequestPack><SessionId>$sessionId</SessionId><Request>products</Request><Param /><scode>10387</scode></SRequestPack>")
        val response = apiService.getProductData(data ?: "")

        if (response.isSuccessful) {
            val productResponse: ProductResponse = parseXml(response.body()?.string() ?: "")

            // Save parsed data into the database
            ProductDao.insertProducts(productResponse.products.map { it.toProduct() })
            productDao.insertBrands(productResponse.brands.map { it.toBrand() })
            productDao.insertGroups(productResponse.groups.map { it.toGroup() })
            productDao.insertPriceGroups(productResponse.priceGroups.map { it.toPriceGroup() })
            productDao.insertUnitTypes(productResponse.unitTypes.map { it.toUnitType() })
        }*/
    }
