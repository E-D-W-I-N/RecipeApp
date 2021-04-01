package com.edwin.recipeapp.presentation.ui.recipesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwin.recipeapp.R
import com.edwin.recipeapp.data.domain.Recipe
import com.edwin.recipeapp.databinding.RecipeItemBinding
import java.text.DateFormat
import java.util.*

class RecipeAdapter(private val listener: OnItemClickListener) :
        ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class RecipeViewHolder(private val binding: RecipeItemBinding) :
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
                Glide.with(itemView)
                        .load(recipe.images.first())
                        .into(imageViewLogo)
                textViewName.text = recipe.name
                textViewDescription.text = recipe.description
                textViewDifficulty.text = itemView.context.getString(R.string.difficulty, recipe.difficulty)
                textViewLastUpdated.text = DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(Date(recipe.lastUpdated * 1000))
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}