package com.edwin.recipeapp.presentation.ui.recipesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwin.recipeapp.databinding.RecipeItemBinding
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.presentation.ui.util.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*

class RecipeAdapter(private val listener: OnItemClickListener<Recipe>) :
    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback()) {

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
                textViewLastUpdated.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    .format(Date(recipe.lastUpdated * 1000))
            }
        }
    }

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

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}