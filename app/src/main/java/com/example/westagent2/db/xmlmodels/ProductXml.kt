package com.example.westagent2.db.xmlmodels

import com.example.westagent2.db.dataentities.Brand
import com.example.westagent2.db.dataentities.Group
import com.example.westagent2.db.dataentities.PriceGroup
import com.example.westagent2.db.dataentities.Product
import com.example.westagent2.db.dataentities.UnitType
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


// "top most" structure of that stupid xml file
@Root(name = "AProductArray", strict = false)
data class ProductResponse(
    @ElementList(name = "Brands", inline = true, required = false) val brands: List<BrandXml>,
    @ElementList(name = "Groups", inline = true, required = false) val groups: List<GroupXml>,
    @ElementList(name = "PriceGroups", inline = true, required = false) val priceGroups: List<PriceGroupXml>,
    @ElementList(name = "UnitTypes", inline = true, required = false) val unitTypes: List<UnitTypeXml>,
    @ElementList(name = "Products", inline = true, required = false) val products: List<ProductXml>
)


@Root(name = "ap", strict = false)
data class ProductXml(
    @Element(name = "a") val id: Int,
    @Element(name = "d") val groupId: Int,
    @Element(name = "e") val brandId: Int,
    @Element(name = "b") val name: String,
    @Element(name = "f") val price: Double,
    @Element(name = "g") val baseunit: Int,
    @Element(name = "h") val caseunit: Int,
    @Element(name = "i") val casesize: Float,
    @Element(name = "j") val stock: Double,
    @Element(name = "k") val color: Int,
    @Element(name = "c") val status: Int,
    @Element(name = "m", required = false) val barcode: String?
)

@Root(name = "ain", strict = false)
data class BrandXml(
    @Element(name = "a") val id: Int,
    @Element(name = "b") val name: String
)

@Root(name = "ain", strict = false)
data class GroupXml(
    @Element(name = "a") val id: Int,
    @Element(name = "b") val name: String
)

@Root(name = "ain", strict = false)
data class PriceGroupXml(
    @Element(name = "a") val id: Int,
    @Element(name = "b") val name: String
)

@Root(name = "ain", strict = false)
data class UnitTypeXml(
    @Element(name = "a") val id: Int,
    @Element(name = "b") val name: String
)


fun ProductXml.toProduct() = Product(
    id = this.id,
    group_id = this.groupId,
    brand_id = this.brandId,
    pricegroup = null, // Adjust if you have the mapping for this
    name = this.name,
    price = this.price,
    baseunit = this.baseunit,
    caseunit = this.caseunit,
    casesize = this.casesize,
    stock = this.stock,
    color = this.color,
    status = this.status,
    barcode = this.barcode ?: ""
)

fun BrandXml.toBrand() = Brand(
    id = this.id,
    name = this.name
)

fun GroupXml.toGroup() = Group(
    id = this.id,
    name = this.name
)

fun PriceGroupXml.toPriceGroup() = PriceGroup(
    id = this.id,
    name = this.name
)

fun UnitTypeXml.toUnitType() = UnitType(
    id = this.id,
    name = this.name
)
