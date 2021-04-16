package com.edwin.recipeapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeBrief(
        val uuid: String,
        val name: String,
        val image: String
) : Parcelable