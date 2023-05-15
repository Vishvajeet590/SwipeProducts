package com.vishwajeet.swipeproducts.ui.productAdd

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUtil
import com.google.android.material.snackbar.Snackbar
import com.vishwajeet.swipeproducts.R
import com.vishwajeet.swipeproducts.databinding.FragmentAddProductBinding
import com.vishwajeet.swipeproducts.models.ProductAddRequest
import com.vishwajeet.swipeproducts.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
@AndroidEntryPoint
class ProductAddFragment : Fragment() {
    private var _binding : FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val productAddViewModel by activityViewModels<ProductAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddProductBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Opening the Image Picker and selecting image on click.
        binding.addImageBtn.setOnClickListener{
            pickImage()
        }

        //Binding the observer of Livedata response
        bindObservers()

        binding.addProductBtn.setOnClickListener {
            val name = binding.productName.text.toString()
            val price = binding.productPrice.text.toString()
            val tax = binding.productTax.text.toString()
            val type = binding.productType.text.toString()
            val req : ProductAddRequest = ProductAddRequest(product_name = name, product_type = type, tax = tax, price = price)

            if (validateProductData(req)){
                //Adding product After validation
                productAddViewModel.addProductAddRequest(req)
            }else{
                Snackbar.make(binding.root, "Few fields are empty", Snackbar.LENGTH_SHORT).show()
            }
        }

        //Going back to Home Screen.
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_productListFragment)
        }

    }

    private fun bindObservers() {
        productAddViewModel.productAddResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.spinner.isVisible = false
            binding.addProductBtn.isActivated = true

            when (it) {
                is NetworkResult.Success -> {
                    //Clearing fields after successful post
                    Snackbar.make(binding.root, "${binding.productName.text} is successfully added.\nGo back to view it.", Snackbar.LENGTH_SHORT).show()
                    binding.productName.text?.clear()
                    binding.productPrice.text?.clear()
                    binding.productTax.text?.clear()
                    binding.productType.text?.clear()
                    binding.productImage.load(R.drawable.imageplaceholder)

                    binding.addProductBtn.isActivated = true

                    binding.spinner.isVisible = false
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context,"Error: ${it.message.toString()}",Toast.LENGTH_SHORT)

                }
                is NetworkResult.Loading ->{
                    binding.spinner.isVisible = true
                    binding.addProductBtn.isActivated = false
                }
            }
        })
    }



    fun validateProductData(req : ProductAddRequest) : Boolean{
        if (req.product_name.trim() == "" || req.price.trim() == "" || req.tax.trim() == "" || req.product_type.trim() == "" ) return false
        return true
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun pickImage(){
        ImagePicker.with(this@ProductAddFragment)
            .galleryOnly().compress(2048)
            .createIntent { intent ->
                startImagePicker.launch(intent)
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private val startImagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                binding.productImage.setImageURI(fileUri)
                val file = context?.let { FileUtil.getTempFile(it,fileUri) }
                if (file != null) {
                    productAddViewModel.addImage(file)
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }



}