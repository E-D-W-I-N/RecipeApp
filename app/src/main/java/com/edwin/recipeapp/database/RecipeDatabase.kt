package com.edwin.recipeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.domain.RecipeDetails

@Database(entities = [Recipe::class, RecipeDetails::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}