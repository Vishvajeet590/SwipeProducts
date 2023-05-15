package com.vishwajeet.swipeproducts.ui.productAdd

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishwajeet.swipeproducts.models.ProductAddRequest
import com.vishwajeet.swipeproducts.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProductAddViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _imageFile = MutableLiveData<File>()
    val imageFile: LiveData<File> get() = _imageFile

    val productAddResponseLiveData get() =productRepository.productAddResponseLiveData


    fun addProductAddRequest(req : ProductAddRequest){
        viewModelScope.launch {
            productRepository.addProduct(imageFile.value,req)
        }
    }

    fun addImage(file : File){
        _imageFile.value = file
    }


}