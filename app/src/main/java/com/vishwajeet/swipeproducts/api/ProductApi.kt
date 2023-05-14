package com.vishwajeet.swipeproducts.api

import com.vishwajeet.swipeproducts.models.ProductListResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("api/public/get")
    suspend fun listProduct() : Response<List<ProductListResponseItem>>
}