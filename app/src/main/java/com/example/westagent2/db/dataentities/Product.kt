package com.example.westagent2.db.dataentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "AProductArray", strict = false)
data class ProductResponse(
    @field:ElementList(name = "Brands", entry = "ain", inline = true, required = false)
    var brands: List<Brand> = emptyList(),

    @field:ElementList(name = "Groups", entry = "ain", inline = true, required = false)
    var groups: List<Group> = emptyList(),

    @field:ElementList(name = "PriceGroups", entry = "ain", inline = true, required = false)
    var priceGroups: List<PriceGroup> = emptyList(),

    @field:ElementList(name = "UnitTypes", entry = "ain", inline = true, required = false)
    var unitTypes: List<UnitType> = emptyList(),

    @field:ElementList(name = "Products", entry = "ap", inline = true, required = false)
    var products: List<Product> = emptyList()
)

// Product Entity with XML Annotations
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
    val barcode: String?
)

@Entity(tableName = "brands")
data class Brand(
    @PrimaryKey
    @field:Path("ain") // Specifies that `id` is within `<ain>`
    @field:Element(name = "a")
    val id: Int,

    @field:Path("ain")
    @field:Element(name = "b")
    val name: String
)

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey
    @field:Path("ain")
    @field:Element(name = "a")
    val id: Int,

    @field:Path("ain")
    @field:Element(name = "b")
    val name: String
)

@Entity(tableName = "price_groups")
data class PriceGroup(
    @PrimaryKey
    @field:Path("ain")
    @field:Element(name = "a")
    val id: Int,

    @field:Path("ain")
    @field:Element(name = "b")
    val name: String
)

@Entity(tableName = "unit_types")
data class UnitType(
    @PrimaryKey
    @field:Path("ain")
    @field:Element(name = "a")
    val id: Int,

    @field:Path("ain")
    @field:Element(name = "b")
    val name: String
)
