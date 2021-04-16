package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwin.recipeapp.databinding.ViewPagerRecommendedItemBinding
import com.edwin.recipeapp.domain.RecipeBrief
import com.edwin.recipeapp.presentation.ui.util.OnItemClickListener

class ViewPagerAdapterRecommended(private val listener: OnItemClickListener<RecipeBrief>) :
    ListAdapter<RecipeBrief, ViewPagerAdapterRecommended.Pager2ViewHolder>(DiffCallback()) {

    inner class Pager2ViewHolder(private val binding: ViewPagerRecommendedItemBinding) :
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
                Glide.with(itemView)
                    .load(similar.image)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapterRecommended.Pager2ViewHolder {
        val binding = ViewPagerRecommendedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewPagerAdapterRecommended.Pager2ViewHolder,
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