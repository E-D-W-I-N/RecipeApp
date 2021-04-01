package com.edwin.recipeapp.data.repository

import androidx.room.withTransaction
import com.edwin.recipeapp.data.database.RecipeDatabase
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
                delay(1000)
                api.getRecipes()
            },
            saveFetchResult = { recipes ->
                db.withTransaction {
                    recipeDao.deleteAllRecipes()
                    recipeDao.insertRecipes(recipes.recipes)
                }
            }
    )
}