package com.vishwajeet.swipeproducts.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.dhaval2404.imagepicker.util.FileUtil
import com.vishwajeet.swipeproducts.api.ProductApi
import com.vishwajeet.swipeproducts.models.ProductAddRequest
import com.vishwajeet.swipeproducts.models.ProductAddResponse
import com.vishwajeet.swipeproducts.models.ProductItem
import com.vishwajeet.swipeproducts.utils.NetworkResult
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    private val _productResponseLiveData = MutableLiveData<NetworkResult<List<ProductItem>>>()
    val productResponseLiveData : LiveData<NetworkResult<List<ProductItem>>> get() = _productResponseLiveData



    private val _productAddResponseLiveData = MutableLiveData<NetworkResult<ProductAddResponse>>()
    val productAddResponseLiveData : LiveData<NetworkResult<ProductAddResponse>> get() = _productAddResponseLiveData
    val MULTIPART_FORM_DATA = "multipart/form-data"



    suspend fun getProductList(){
        _productResponseLiveData.postValue(NetworkResult.Loading())
        val response = productApi.listProduct()
        if(response.isSuccessful && response.body() != null){
            _productResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if (response.errorBody() != null){
            _productResponseLiveData.postValue(NetworkResult.Error(response.errorBody().toString()))
        }
    }

    suspend fun addProduct(image: File?, productRequest: ProductAddRequest){

        val response : Response<ProductAddResponse>
        _productAddResponseLiveData.postValue(NetworkResult.Loading())
        if (image == null){
            response = productApi.addProduct(createRequestBody(productRequest.product_name),createRequestBody(productRequest.price)
                ,createRequestBody(productRequest.product_type),createRequestBody(productRequest.tax), null)

        }else{
            response = productApi.addProduct(createRequestBody(productRequest.product_name),createRequestBody(productRequest.price)
                ,createRequestBody(productRequest.product_type),createRequestBody(productRequest.tax), MultipartBody.Part.createFormData("files[]",
                    image.name, image!!.asRequestBody()))

        }


        if(response.isSuccessful && response.body() != null){
            _productAddResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if (response.errorBody() != null){
            _productAddResponseLiveData.postValue(NetworkResult.Error(response.errorBody().toString()))
        }
    }



    fun createRequestBody(s: String, type: String? = MULTIPART_FORM_DATA): RequestBody {
        return s.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
    }




}