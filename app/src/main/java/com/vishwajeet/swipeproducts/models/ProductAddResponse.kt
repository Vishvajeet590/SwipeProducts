package com.vishwajeet.swipeproducts.models

data class ProductAddResponse(
    val message: String,
    val product_details: ProductItem,
    val product_id: Int,
    val success: Boolean
)