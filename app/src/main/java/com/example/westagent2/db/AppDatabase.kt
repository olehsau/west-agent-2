package com.example.westagent2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.westagent2.db.daos.ProductDao
import com.example.westagent2.db.dataentities.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}