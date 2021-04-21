package com.edwin.recipeapp.presentation.util

fun interface OnItemClickListener<T> {
    fun onItemClick(item: T)
}