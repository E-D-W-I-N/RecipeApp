package com.edwin.recipeapp.di

import com.edwin.recipeapp.database.RecipeDatabase
import com.edwin.recipeapp.network.RecipeApi
import com.edwin.recipeapp.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
            api: RecipeApi,
            db: RecipeDatabase
    ): RecipeRepository {
        return RecipeRepository(
                api = api,
                db = db
        )
    }
}