package com.oshanti.livedataretrofit.data.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    var count: Int,
    var next: String?,
    var previous: Any?,
    @SerializedName("results")
    var recipes: List<Recipe>
)