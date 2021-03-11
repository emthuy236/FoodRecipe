package com.example.foodappnew.ui.fragments

import MainViewModel
import adapters.RecipesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodappnew.databinding.FragmentRecipeBinding
import dagger.hilt.android.AndroidEntryPoint
import ultil.Constants.Companion.API_KEY
import ultil.NetworkResult
@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private val madapter by lazy { RecipesAdapter() }
    private lateinit var  mainViewModel : MainViewModel
    private var binding1:FragmentRecipeBinding? = null
    private val _binding get() = binding1!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding1 =  FragmentRecipeBinding.inflate(layoutInflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
       setupRecyclerview()
        requestApi()
        return _binding.root
    }
    private fun requestApi(){
        mainViewModel.getrecipe(applyQueries())
        mainViewModel.recipeRespone. observe(viewLifecycleOwner, Observer{ response ->
            when(response){
                is NetworkResult.Success ->{
                    showShimmerEffect()
                    response.data?.let { madapter.setdata(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(),response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    hideShimmerEffect()
                }

            }
        })

    }
    private fun applyQueries():HashMap<String,String>{
        val queries = HashMap<String,String>()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }
    private fun setupRecyclerview(){
        binding1!!.recyclerview.adapter = madapter
       binding1!!.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        binding1!!.recyclerview.showShimmer()
    }
    private  fun hideShimmerEffect(){
        binding1!!.recyclerview.hideShimmer()
    }


}