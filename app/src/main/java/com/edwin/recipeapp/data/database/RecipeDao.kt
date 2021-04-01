package com.edwin.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edwin.recipeapp.data.domain.Recipe
import com.edwin.recipeapp.util.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    fun getRecipes(query: String, sortOrder: SortOrder): Flow<List<Recipe>> =
            when (sortOrder) {
                SortOrder.BY_NAME -> getAllRecipesByName(query)
                SortOrder.BY_DATE -> getAllRecipesByDate(query)
                SortOrder.BY_DIFFICULTY -> getAllRecipesByDifficulty(query)
            }

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name")
    fun getAllRecipesByName(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchQuery || '%' ORDER BY lastUpdated")
    fun getAllRecipesByDate(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :searchQuery || '%' ORDER BY difficulty")
    fun getAllRecipesByDifficulty(searchQuery: String): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()
}