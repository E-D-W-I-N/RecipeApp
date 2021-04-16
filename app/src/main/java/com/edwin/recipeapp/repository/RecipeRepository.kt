package com.edwin.recipeapp.repository

import androidx.room.withTransaction
import com.edwin.recipeapp.database.RecipeDatabase
import com.edwin.recipeapp.network.RecipeApi
import com.edwin.recipeapp.util.SortOrder
import com.edwin.recipeapp.util.networkBoundResource
import kotlinx.coroutines.delay
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
            delay(3000)
            api.getRecipes()
        },
        saveFetchResult = { recipes ->
            db.withTransaction {
                recipeDao.deleteAllRecipes()
                recipeDao.insertRecipes(recipes.recipes)
            }
        }
    )

    fun getRecipe(uuid: String) = networkBoundResource(
        query = {
            recipeDao.getRecipe(uuid)
        },
        fetch = {
            delay(3000)
            api.getRecipe(uuid)
        },
        saveFetchResult = { response ->
            db.withTransaction {
                recipeDao.deleteRecipe(response.recipe)
                recipeDao.insertRecipe(response.recipe)
            }
        }
    )
}