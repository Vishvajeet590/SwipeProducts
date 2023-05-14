package com.vishwajeet.swipeproducts.ui.productList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vishwajeet.swipeproducts.ui.productList.ProductListViewModel
import com.vishwajeet.swipeproducts.R
import com.vishwajeet.swipeproducts.databinding.FragmentProductListBinding
import com.vishwajeet.swipeproducts.models.ProductListResponseItem
import com.vishwajeet.swipeproducts.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val productListViewModel by activityViewModels<ProductListViewModel>()

    private lateinit var adapter : ProductListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)

        adapter = ProductListAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListViewModel.listProduct()


        binding.productListRecycler.setHasFixedSize(true)
        binding.productListRecycler.layoutManager =  LinearLayoutManager(context)
        binding.productListRecycler.adapter = adapter
        bindObservers()


    }


    private fun bindObservers() {
        productListViewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                    Log.d("FRAG",it.data.toString())

                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading ->{

                }
            }
        })
    }

}