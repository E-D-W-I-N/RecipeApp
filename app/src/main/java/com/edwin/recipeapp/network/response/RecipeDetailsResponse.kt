package com.edwin.recipeapp.network.response

import com.edwin.recipeapp.domain.RecipeDetails
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    @SerializedName("recipe")
    val recipeDetails: RecipeDetails
)