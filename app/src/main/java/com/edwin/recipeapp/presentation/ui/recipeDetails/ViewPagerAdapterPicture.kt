package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwin.recipeapp.databinding.ViewPagerPictureItemBinding
import com.edwin.recipeapp.presentation.ui.util.OnItemClickListener

class ViewPagerAdapterPicture(private val listener: OnItemClickListener<String>) :
    ListAdapter<String, ViewPagerAdapterPicture.Pager2ViewHolder>(DiffCallback()) {

    inner class Pager2ViewHolder(private val binding: ViewPagerPictureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(image: String) {
            binding.apply {
                Glide.with(itemView)
                    .load(image)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapterPicture.Pager2ViewHolder {
        val binding = ViewPagerPictureItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapterPicture.Pager2ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}