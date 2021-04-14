package com.edwin.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edwin.recipeapp.data.domain.Recipe
import com.edwin.recipeapp.data.domain.RecipeDetails
import com.edwin.recipeapp.util.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    fun getRecipes(query: String, sortOrder: SortOrder): Flow<List<Recipe>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> getAllRecipesByName(query)
            SortOrder.BY_DATE -> getAllRecipesByDate(query)
        }

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name")
    fun getAllRecipesByName(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchQuery || '%' ORDER BY lastUpdated")
    fun getAllRecipesByDate(searchQuery: String): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()

    @Query("SELECT * FROM recipeDetails WHERE uuid == :uuid")
    fun getRecipeDetails(uuid: String): Flow<RecipeDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeDetails(recipeDetails: RecipeDetails)

    @Query("DELETE FROM recipeDetails WHERE uuid == :uuid")
    suspend fun deleteRecipeDetails(uuid: String)
}