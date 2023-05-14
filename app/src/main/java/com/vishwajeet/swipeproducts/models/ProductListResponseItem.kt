package com.vishwajeet.swipeproducts.models

data class ProductListResponseItem(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)