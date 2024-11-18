package com.example.westagent2.db.dataentities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Product Entity
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val group_id: Int?,
    val brand_id: Int?,
    val pricegroup: Int?,
    val name: String,
    val price: Double,
    val baseunit: Int,
    val caseunit: Int,
    val casesize: Float,
    val stock: Double,
    val color: Int,
    val status: Int,
    val barcode: String
)

// Brand Entity
@Entity(tableName = "brands")
data class Brand(
    @PrimaryKey val id: Int,
    val name: String
)

// Group Entity
@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val id: Int,
    val name: String
)

// PriceGroup Entity
@Entity(tableName = "price_groups")
data class PriceGroup(
    @PrimaryKey val id: Int,
    val name: String
)

// UnitType Entity
@Entity(tableName = "unit_types")
data class UnitType(
    @PrimaryKey val id: Int,
    val name: String
)
