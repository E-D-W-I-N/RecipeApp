package com.edwin.recipeapp.data.database

import androidx.room.*
import com.edwin.recipeapp.data.util.SortOrder
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.domain.RecipeDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    fun getRecipes(query: String, sortOrder: SortOrder): Flow<List<Recipe>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> getAllRecipesByName("%$query%")
            SortOrder.BY_DATE -> getAllRecipesByDate("%$query%")
        }

    @Transaction
    suspend fun deleteAndInsertRecipesTransaction(recipes: List<Recipe>) {
        deleteAllRecipes()
        insertRecipes(recipes)
    }

    @Transaction
    suspend fun deleteAndInsertRecipeDetailsTransaction(recipeDetails: RecipeDetails) {
        deleteRecipeDetails(recipeDetails)
        insertRecipeDetails(recipeDetails)
    }

    @Query("SELECT * FROM recipe WHERE name LIKE :searchQuery OR description LIKE :searchQuery OR instructions LIKE :searchQuery ORDER BY name")
    fun getAllRecipesByName(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE name LIKE :searchQuery OR description LIKE :searchQuery OR instructions LIKE :searchQuery ORDER BY lastUpdated")
    fun getAllRecipesByDate(searchQuery: String): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()

    @Query("SELECT * FROM recipeDetails WHERE uuid == :uuid")
    fun getRecipeDetails(uuid: String): Flow<RecipeDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeDetails(recipeDetails: RecipeDetails)

    @Delete
    suspend fun deleteRecipeDetails(recipeDetails: RecipeDetails)
}