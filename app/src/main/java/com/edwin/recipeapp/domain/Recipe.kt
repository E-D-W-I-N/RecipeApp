package com.edwin.recipeapp.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
        @PrimaryKey val uuid: String,
        val name: String,
        val images: List<String>,
        val lastUpdated: Long,
        val description: String?,
        val instructions: String?,
        val difficulty: Int,
        val similar: List<RecipeBrief>?
) : Parcelable