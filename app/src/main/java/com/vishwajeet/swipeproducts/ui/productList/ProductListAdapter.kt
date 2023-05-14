package com.vishwajeet.swipeproducts.ui.productList

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vishwajeet.swipeproducts.R
import com.vishwajeet.swipeproducts.databinding.ProductListCardBinding
import com.vishwajeet.swipeproducts.models.ProductListResponseItem
import kotlin.random.Random

class ProductListAdapter() : ListAdapter<ProductListResponseItem, ProductListAdapter.ProductListviewHolder>(ComparatorDiffUtil()) {

    inner class ProductListviewHolder(private val binding: ProductListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductListResponseItem) {
            binding.name.text = product.product_name
            binding.type.text = product.product_type
            binding.tax.text = product.tax.toString()
            binding.price.text = String.format("%.2f", product.price)+" $"

            if (!product.image.isEmpty()){
                binding.image.load(product.image) {
                    placeholder(R.drawable.imageplaceholder)
                }
            }else{
                binding.image.load(R.drawable.imageplaceholder)
            }

            //changing the color of
            val currentColor = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256));
            binding.card.setCardBackgroundColor(currentColor)
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<ProductListResponseItem>() {
        override fun areItemsTheSame(oldItem: ProductListResponseItem, newItem: ProductListResponseItem): Boolean {
            return oldItem.product_name == newItem.product_name
        }

        override fun areContentsTheSame(oldItem: ProductListResponseItem, newItem: ProductListResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListviewHolder {
        val binding = ProductListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListviewHolder(binding)    }

    override fun onBindViewHolder(holder: ProductListviewHolder, position: Int) {
        val product = getItem(position)

        product?.let {
            holder.bind(it)
        }
    }

}