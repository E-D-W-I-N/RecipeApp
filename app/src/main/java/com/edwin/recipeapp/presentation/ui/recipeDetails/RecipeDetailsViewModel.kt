package com.edwin.recipeapp.presentation.ui.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.edwin.recipeapp.data.domain.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
        state: SavedStateHandle
) : ViewModel() {
    val recipe = state.get<Recipe>("Recipe")
}