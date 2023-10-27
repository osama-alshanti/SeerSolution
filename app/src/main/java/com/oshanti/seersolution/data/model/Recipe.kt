package com.oshanti.livedataretrofit.data.model

data class Recipe(
    var cooking_instructions: Any?= null,
    var date_added: String?= null,
    var date_updated: String?= null,
    var description: String?= null,
    var featured_image: String?= null,
    var ingredients: List<String>?= null,
    val long_date_added: Int,
    val long_date_updated: Int,
    val pk: Int,
    val publisher: String,
    val rating: Int,
    val source_url: String,
    var title: String?= null
)