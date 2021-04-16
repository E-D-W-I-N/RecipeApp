package com.edwin.recipeapp.presentation.ui.util

fun interface OnItemClickListener<T> {
    fun onItemClick(item: T)
}