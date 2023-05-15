package com.vishwajeet.swipeproducts.api

import com.vishwajeet.swipeproducts.models.ProductAddRequest
import com.vishwajeet.swipeproducts.models.ProductAddResponse
import com.vishwajeet.swipeproducts.models.ProductItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProductApi {
    @GET("api/public/get")
    suspend fun listProduct() : Response<List<ProductItem>>

   /* @POST("api/public/add")
    @Multipart
    suspend fun addProduct(@PartMap partMap: HashMap<String, @JvmSuppressWildcards RequestBody>, @Part image: MultipartBody.Part) : Response<ProductAddResponse>
*/

    @POST("api/public/add")
    @Multipart
    suspend fun addProduct(@Part("product_name") name : RequestBody, @Part("price") price : RequestBody, @Part("product_type") type : RequestBody,@Part("tax") tax : RequestBody, @Part image: MultipartBody.Part? ) : Response<ProductAddResponse>

}