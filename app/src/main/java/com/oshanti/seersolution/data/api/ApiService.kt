package com.oshanti.seersolution.data.api

import androidx.lifecycle.LiveData
import com.oshanti.livedataretrofit.data.model.RecipeResponse
import com.oshanti.seersolution.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    @Headers("Authorization: $API_KEY")
    suspend fun getAllRecipes(@Query("page") page:String,
                              @Query("query") query: String): RecipeResponse

}