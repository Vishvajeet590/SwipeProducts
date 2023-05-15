package com.vishwajeet.swipeproducts.ui.productList

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vishwajeet.swipeproducts.repository.ProductRepository
import com.vishwajeet.swipeproducts.utils.ConnectivityObserver
import com.vishwajeet.swipeproducts.utils.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    application: Application,
    private val productRepository: ProductRepository
) : AndroidViewModel(application) {

    val productListLiveData get() = productRepository.productResponseLiveData
    private val context = getApplication<Application>().applicationContext

    private lateinit var connectivityObserver: ConnectivityObserver
    fun listProduct() {
        connectivityObserver = NetworkConnectivityObserver(context)
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available){
                productRepository.getProductList()
            }else{
                Toast.makeText(context,"Internet is not available.", Toast.LENGTH_LONG)
            }
        }.launchIn(viewModelScope)
    }

}