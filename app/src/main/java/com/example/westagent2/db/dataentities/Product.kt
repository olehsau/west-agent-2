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
    @PrimaryKey
    @field:Element(name = "a")
    val id: Int,

    @field:Element(name = "d", required = false)
    val group_id: Int?,

    @field:Element(name = "e", required = false)
    val brand_id: Int?,

    @field:Element(name = "pricegroup", required = false)
    val pricegroup: Int?,

    @field:Element(name = "b")
    val name: String,

    @field:Element(name = "f")
    val price: Double,

    @field:Element(name = "g", required = false)
    val baseunit: Int,

    @field:Element(name = "h", required = false)
    val caseunit: Int,

    @field:Element(name = "i", required = false)
    val casesize: Float,

    @field:Element(name = "j", required = false)
    val stock: Double,

    @field:Element(name = "k", required = false)
    val color: Int,

    @field:Element(name = "c", required = false)
    val status: Int,

    @field:Element(name = "m", required = false)
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
