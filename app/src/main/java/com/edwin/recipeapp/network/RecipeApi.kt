package com.edwin.recipeapp.network

import com.edwin.recipeapp.network.response.RecipeListResponse
import com.edwin.recipeapp.network.response.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(): RecipeListResponse

    @GET("recipes/{uuid}")
    suspend fun getRecipe(@Path("uuid") uuid: String): RecipeResponse
}