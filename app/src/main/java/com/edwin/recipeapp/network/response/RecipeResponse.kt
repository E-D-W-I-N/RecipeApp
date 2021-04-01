package com.edwin.recipeapp.network.response

import com.edwin.recipeapp.data.domain.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
        @SerializedName("recipes")
        val recipes: List<Recipe>
)