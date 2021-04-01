package com.edwin.recipeapp.di

import com.edwin.recipeapp.network.RecipeApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi {
        return Retrofit.Builder()
                .baseUrl("https://test.kode-t.ru/")
                .addConverterFactory(create(GsonBuilder().create()))
                .build().create(RecipeApi::class.java)
    }
}