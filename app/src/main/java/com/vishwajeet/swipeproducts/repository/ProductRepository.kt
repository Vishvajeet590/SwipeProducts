package com.vishwajeet.swipeproducts.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vishwajeet.swipeproducts.api.ProductApi
import com.vishwajeet.swipeproducts.models.ProductListResponseItem
import com.vishwajeet.swipeproducts.utils.NetworkResult
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    private val _productResponseLiveData = MutableLiveData<NetworkResult<List<ProductListResponseItem>>>()
    val productResponseLiveData : LiveData<NetworkResult<List<ProductListResponseItem>>> get() = _productResponseLiveData


    suspend fun getProductList(){
        val response = productApi.listProduct()
        if(response.isSuccessful && response.body() != null){
            _productResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if (response.errorBody() != null){
            _productResponseLiveData.postValue(NetworkResult.Error(response.errorBody().toString()))
        }

    }
}