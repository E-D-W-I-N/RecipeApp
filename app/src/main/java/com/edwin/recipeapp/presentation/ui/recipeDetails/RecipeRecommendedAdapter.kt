package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwin.recipeapp.databinding.ViewPagerRecommendedItemBinding
import com.edwin.recipeapp.domain.RecipeBrief
import com.edwin.recipeapp.presentation.util.OnItemClickListener
import com.edwin.recipeapp.presentation.util.loadImage

class RecipeRecommendedAdapter(private val listener: OnItemClickListener<RecipeBrief>) :
        ListAdapter<RecipeBrief, RecipeRecommendedAdapter.RecommendedRecipesViewHolder>(DiffCallback()) {

    inner class RecommendedRecipesViewHolder(private val binding: ViewPagerRecommendedItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(similar: RecipeBrief) {
            binding.apply {
                textView.text = similar.name
                imageView.loadImage(itemView.context, similar.image)
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecipeRecommendedAdapter.RecommendedRecipesViewHolder {
        val binding = ViewPagerRecommendedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return RecommendedRecipesViewHolder(binding)
    }

    override fun onBindViewHolder(
            holder: RecipeRecommendedAdapter.RecommendedRecipesViewHolder,
            position: Int
    ) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<RecipeBrief>() {
        override fun areItemsTheSame(oldItem: RecipeBrief, newItem: RecipeBrief): Boolean =
                oldItem.uuid === newItem.uuid

        override fun areContentsTheSame(oldItem: RecipeBrief, newItem: RecipeBrief): Boolean =
                oldItem == newItem
    }
}