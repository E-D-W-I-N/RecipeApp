package com.edwin.recipeapp.presentation.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(context: Context, imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(ColorDrawable(Color.WHITE))
        .into(this)
}