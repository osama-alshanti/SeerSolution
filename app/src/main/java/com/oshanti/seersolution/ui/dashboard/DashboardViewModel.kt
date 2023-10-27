package com.oshanti.seersolution.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oshanti.livedataretrofit.data.model.Recipe
import com.oshanti.livedataretrofit.data.repository.RecipeRepo
import com.oshanti.seersolution.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val recipeRepo: RecipeRepo) : ViewModel() {

    private val recipeMutableLiveData: MutableLiveData<UiState<List<Recipe>>> =
        MutableLiveData(UiState.Loading)
    var recipeLiveData: LiveData<UiState<List<Recipe>>> = recipeMutableLiveData

    private val _recipeSearch: MutableStateFlow<UiState<List<Recipe>>> =
        MutableStateFlow(UiState.Loading)
    var recipeSearch: StateFlow<UiState<List<Recipe>>> = _recipeSearch

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("TAG", "$exception handled !")
    }

    init {
        getAllRecipe()
    }

    fun search(page: String, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepo.search(page, query)
                .catch {
                    _recipeSearch.value = UiState.Error(it.message.toString())
                }
                .collect {
                    Log.d("TAG", "search: "+it.size)
                    _recipeSearch.value = UiState.Success(it)
                }
        }
    }

    private fun getAllRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = recipeRepo.getAllRecipes()
                recipeMutableLiveData.postValue(UiState.Success(recipes))
            } catch (e: Exception) {
                recipeMutableLiveData.postValue(UiState.Error(e.message.toString()))
            }
        }
    }


}