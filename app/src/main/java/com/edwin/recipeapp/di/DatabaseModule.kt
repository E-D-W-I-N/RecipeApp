package com.edwin.recipeapp.di

import android.app.Application
import androidx.room.Room
import com.edwin.recipeapp.data.database.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
            Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe_database")
                    .build()

    @Provides
    fun provideRecipeDao(database: RecipeDatabase) = database.recipeDao()
}