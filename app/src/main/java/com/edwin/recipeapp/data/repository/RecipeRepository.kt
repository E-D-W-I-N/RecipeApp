package com.edwin.recipeapp.data.repository

import com.edwin.recipeapp.data.database.RecipeDao
import com.edwin.recipeapp.data.network.RecipeApi
import com.edwin.recipeapp.data.util.SortOrder
import com.edwin.recipeapp.data.util.networkBoundResource
import javax.inject.Inject

class RecipeRepository @Inject constructor(
        private val recipeApi: RecipeApi,
        private val recipeDao: RecipeDao
) {
    fun getRecipes(query: String, sortOrder: SortOrder) = networkBoundResource(
            query = {
                recipeDao.getRecipes(query, sortOrder)
            },
            fetch = {
                recipeApi.getRecipes()
            },
            saveFetchResult = { response ->
                recipeDao.deleteAndInsertRecipesTransaction(response.recipes)
            }
    )

    fun getRecipeDetails(uuid: String) = networkBoundResource(
            query = {
                recipeDao.getRecipeDetails(uuid)
            },
            fetch = {
                recipeApi.getRecipeDetails(uuid)
            },
            saveFetchResult = { response ->
                recipeDao.deleteAndInsertRecipeDetailsTransaction(response.recipeDetails)
            }
    )
}