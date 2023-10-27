package com.oshanti.livedataretrofit.data.repository

import com.oshanti.seersolution.data.api.ApiService
import com.oshanti.livedataretrofit.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepo @Inject constructor(private val apiService: ApiService) {


      suspend fun getAllRecipes(): List<Recipe> = apiService.getAllRecipes("1", "").recipes

      suspend fun search(page: String, query: String): Flow<List<Recipe>> = flow {
            emit(apiService.getAllRecipes(page, query))
      }.map {
            it.recipes
      }
}