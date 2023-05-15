package com.vishwajeet.swipeproducts.models

import android.net.Uri

data class ProductAddRequest(
    val price: String,
    val product_name: String,
    val product_type: String,
    val tax: String
)
