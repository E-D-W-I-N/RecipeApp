package com.edwin.recipeapp.network

import com.edwin.recipeapp.network.response.RecipeDetailsResponse
import com.edwin.recipeapp.network.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(): RecipeListResponse

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsResponse
}