package com.edwin.recipeapp.presentation.ui.recipesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwin.recipeapp.databinding.RecipeItemBinding
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.presentation.util.OnItemClickListener
import com.edwin.recipeapp.presentation.util.loadImage

class RecipeListAdapter(private val listener: OnItemClickListener<Recipe>) :
        ListAdapter<Recipe, RecipeListAdapter.RecipeListViewHolder>(DiffCallback()) {

    inner class RecipeListViewHolder(private val binding: RecipeItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(recipe: Recipe) {
            binding.apply {
                imageViewLogo.loadImage(itemView.context, recipe.images.first())
                textViewName.text = recipe.name
                textViewDescription.text = recipe.description
                textViewLastUpdated.text = recipe.updateDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding = RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holderList: RecipeListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holderList.bind(currentItem)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}