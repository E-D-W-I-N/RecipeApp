package com.edwin.recipeapp.database

import androidx.room.*
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.util.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    fun getRecipes(query: String, sortOrder: SortOrder): Flow<List<Recipe>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> getAllRecipesByName(query)
            SortOrder.BY_DATE -> getAllRecipesByDate(query)
        }

    @Query("SELECT * FROM recipe WHERE name OR description OR instructions LIKE '%' || :searchQuery || '%' ORDER BY name")
    fun getAllRecipesByName(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE name OR description OR instructions LIKE '%' || :searchQuery || '%' ORDER BY lastUpdated")
    fun getAllRecipesByDate(searchQuery: String): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()

    @Query("SELECT * FROM recipe WHERE uuid == :uuid")
    fun getRecipe(uuid: String): Flow<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}