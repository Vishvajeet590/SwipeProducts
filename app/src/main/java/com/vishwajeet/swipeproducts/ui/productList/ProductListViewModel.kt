package com.vishwajeet.swipeproducts.ui.productList

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.vishwajeet.swipeproducts.ProductApplication
import com.vishwajeet.swipeproducts.repository.ProductRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    application: Application,
    private val productRepository: ProductRepository
) : AndroidViewModel(application) {

    val productListLiveData get() = productRepository.productResponseLiveData

    fun listProduct() {
        viewModelScope.launch {
            productRepository.getProductList()
        }
    }

}