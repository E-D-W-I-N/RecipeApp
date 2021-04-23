package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwin.recipeapp.databinding.ViewPagerPictureItemBinding
import com.edwin.recipeapp.presentation.util.OnItemClickListener
import com.edwin.recipeapp.presentation.util.loadImage

class RecipePictureAdapter(private val listener: OnItemClickListener<String>) :
    ListAdapter<String, RecipePictureAdapter.PictureViewHolder>(DiffCallback()) {

    inner class PictureViewHolder(private val binding: ViewPagerPictureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(getItem(position))
            }
        }

        fun bind(image: String) {
            binding.imageView.loadImage(itemView.context, image)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipePictureAdapter.PictureViewHolder {
        val binding = ViewPagerPictureItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipePictureAdapter.PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}