package com.example.westagent2.db.dataentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "AProductArray", strict = false)
data class ProductResponse(
    @field:ElementList(name = "Brands", inline = true, required = false) val brands: List<Brand>,
    @field:ElementList(name = "Groups", inline = true, required = false) val groups: List<Group>,
    @field:ElementList(name = "PriceGroups", inline = true, required = false) val priceGroups: List<PriceGroup>,
    @field:ElementList(name = "UnitTypes", inline = true, required = false) val unitTypes: List<UnitType>,
    @field:ElementList(name = "Products", inline = true, required = false) val products: List<Product>
)



// Product Entity with XML Annotations
@Entity(tableName = "products")
@Root(name = "ap", strict = false)
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

// Brand Entity with XML Annotations
@Entity(tableName = "brands")
@Root(name = "ain", strict = false)
data class Brand(
    @PrimaryKey
    @field:Element(name = "a")
    val id: Int,

    @field:Element(name = "b")
    val name: String
)

// Group Entity with XML Annotations
@Entity(tableName = "groups")
@Root(name = "ain", strict = false)
data class Group(
    @PrimaryKey
    @field:Element(name = "a")
    val id: Int,

    @field:Element(name = "b")
    val name: String
)

// PriceGroup Entity with XML Annotations
@Entity(tableName = "price_groups")
@Root(name = "ain", strict = false)
data class PriceGroup(
    @PrimaryKey
    @field:Element(name = "a")
    val id: Int,

    @field:Element(name = "b")
    val name: String
)

// UnitType Entity with XML Annotations
@Entity(tableName = "unit_types")
@Root(name = "ain", strict = false)
data class UnitType(
    @PrimaryKey
    @field:Element(name = "a")
    val id: Int,

    @field:Element(name = "b")
    val name: String
)

