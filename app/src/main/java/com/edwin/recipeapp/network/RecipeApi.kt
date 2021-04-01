package com.edwin.recipeapp.network

import com.edwin.recipeapp.network.response.RecipeResponse
import retrofit2.http.GET

interface RecipeApi {

    @GET("recipes.json")
    suspend fun getRecipes(): RecipeResponse
}