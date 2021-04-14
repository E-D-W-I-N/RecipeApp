package com.edwin.recipeapp.presentation.ui.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.edwin.recipeapp.data.domain.Recipe
import com.edwin.recipeapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    repository: RecipeRepository,
    state: SavedStateHandle
) : ViewModel() {
    val recipe = state.get<Recipe>("Recipe")

    val recipeDetails = recipe?.uuid?.let { repository.getRecipeDetails(it) }?.asLiveData()
}