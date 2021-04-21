package com.edwin.recipeapp.data.network

import com.edwin.recipeapp.data.network.response.RecipeDetailsResponse
import com.edwin.recipeapp.data.network.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(): RecipeListResponse

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsResponse
}