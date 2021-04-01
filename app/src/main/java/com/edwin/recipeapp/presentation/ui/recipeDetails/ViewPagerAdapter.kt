package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edwin.recipeapp.databinding.ViewPagerItemBinding

class ViewPagerAdapter(private var images: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(private val binding: ViewPagerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {
            binding.apply {
                Glide.with(itemView)
                        .load(image)
                        .apply(RequestOptions().override(800, 800))
                        .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        val binding = ViewPagerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}