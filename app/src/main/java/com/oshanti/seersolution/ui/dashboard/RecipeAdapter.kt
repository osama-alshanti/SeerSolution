package com.oshanti.seersolution.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.oshanti.livedataretrofit.data.model.Recipe
import com.oshanti.seersolution.databinding.RecipeItemBinding

class RecipeAdapter(private val recipes: ArrayList<Recipe>): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    fun setData(recipeList: List<Recipe>){
        recipes.clear()
        recipes.addAll(recipeList)
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val binding: RecipeItemBinding): ViewHolder(binding.root){

        fun bind(recipe: Recipe){
            Glide.with(itemView.context).load(recipe.featured_image).into(binding.imageView)
            binding.tvRecipeRating.text = recipe.rating.toString()
            binding.tvRecipeTitle.text = recipe.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
            holder.bind(recipe)

    }
}