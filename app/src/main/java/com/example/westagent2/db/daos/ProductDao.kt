package com.example.westagent2.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.westagent2.db.dataentities.Brand
import com.example.westagent2.db.dataentities.Group
import com.example.westagent2.db.dataentities.PriceGroup
import com.example.westagent2.db.dataentities.Product
import com.example.westagent2.db.dataentities.UnitType

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Int): Product?

    @Query("SELECT * FROM products WHERE name LIKE :name")
    suspend fun getProductsByName(name: String): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProducts(products: List<Product>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrands(brands: List<Brand>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(groups: List<Group>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceGroups(priceGroups: List<PriceGroup>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitTypes(unitTypes: List<UnitType>)
    
    // todo query to get quantity in stock

}
