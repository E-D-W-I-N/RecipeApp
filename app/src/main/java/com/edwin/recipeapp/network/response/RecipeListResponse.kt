package com.edwin.recipeapp.network.response

import com.edwin.recipeapp.domain.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeListResponse(
        @SerializedName("recipes")
        val recipes: List<Recipe>
)