package com.oshanti.seersolution.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.oshanti.seersolution.base.UiState
import com.oshanti.seersolution.databinding.ActivityDashboardBinding
import com.oshanti.seersolution.utils.Extensions.setVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private var _binding: ActivityDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeAdapterHorizontal: RecipeAdapterHorizontal
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()
        setObserver()

        binding.editSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val txt = p0.toString()
                dashboardViewModel.search("1", txt)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    private fun setUpAdapter(){
        recipeAdapterHorizontal = RecipeAdapterHorizontal(ArrayList())
        binding.rvHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorizontal.adapter = recipeAdapterHorizontal

        recipeAdapter = RecipeAdapter(ArrayList())
        binding.rvVertical.adapter = recipeAdapter
    }

    private fun setObserver(){

        dashboardViewModel.recipeLiveData.observe(this){
            when(it){
                is UiState.Error ->{
                    binding.progress.setVisibility(false)
                    binding.rvVertical.setVisibility(true)

                    Toast.makeText(this, "error "+it.msg, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success ->{
                    binding.progress.setVisibility(false)
                    binding.rvVertical.setVisibility(true)

                    recipeAdapter.setData(it.data)
                    recipeAdapterHorizontal.setData(it.data)
                }
                is UiState.Loading ->{
                    binding.progress.setVisibility(true)
                    binding.rvVertical.setVisibility(false)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            dashboardViewModel.recipeSearch.collect{

                when(it){
                    is UiState.Error ->{
                        binding.progress.setVisibility(false)
                        binding.rvVertical.setVisibility(false)

                        Toast.makeText(this@DashboardActivity, "error "+it.msg, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success ->{
                        binding.progress.setVisibility(false)
                        binding.rvVertical.setVisibility(true)

                        recipeAdapter.setData(it.data)
                    }
                    is UiState.Loading ->{
                        binding.progress.setVisibility(true)
                        binding.rvVertical.setVisibility(false)
                    }
                }

            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}