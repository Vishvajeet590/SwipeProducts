package com.vishwajeet.swipeproducts.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishwajeet.swipeproducts.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val productListLiveData get() =productRepository.productResponseLiveData

    fun listProduct() {
        viewModelScope.launch {
            productRepository.getProductList()
        }
    }
}