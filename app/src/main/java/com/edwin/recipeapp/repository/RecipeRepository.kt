package com.edwin.recipeapp.repository

import androidx.room.withTransaction
import com.edwin.recipeapp.database.RecipeDatabase
import com.edwin.recipeapp.network.RecipeApi
import com.edwin.recipeapp.util.SortOrder
import com.edwin.recipeapp.util.networkBoundResource
import javax.inject.Inject

class RecipeRepository @Inject constructor(
        private val api: RecipeApi,
        private val db: RecipeDatabase
) {
    private val recipeDao = db.recipeDao()

    fun getRecipes(query: String, sortOrder: SortOrder) = networkBoundResource(
            query = {
                recipeDao.getRecipes(query, sortOrder)
            },
            fetch = {
                api.getRecipes()
            },
            saveFetchResult = { recipes ->
                db.withTransaction {
                    recipeDao.deleteAllRecipes()
                    recipeDao.insertRecipes(recipes.recipes)
                }
            }
    )

    fun getRecipeDetails(uuid: String) = networkBoundResource(
            query = {
                recipeDao.getRecipeDetails(uuid)
            },
            fetch = {
                api.getRecipeDetails(uuid)
            },
            saveFetchResult = { response ->
                db.withTransaction {
                    recipeDao.deleteRecipeDetails(response.recipeDetails)
                    recipeDao.insertRecipeDetails(response.recipeDetails)
                }
            }
    )
}