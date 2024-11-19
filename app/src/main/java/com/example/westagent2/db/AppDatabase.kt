package com.example.westagent2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.westagent2.db.daos.ProductDao
import com.example.westagent2.db.dataentities.Brand
import com.example.westagent2.db.dataentities.Group
import com.example.westagent2.db.dataentities.PriceGroup
import com.example.westagent2.db.dataentities.Product
import com.example.westagent2.db.dataentities.UnitType

@Database(entities = [Product::class, Brand::class, Group::class, PriceGroup::class, UnitType::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun productDao(): ProductDao

}