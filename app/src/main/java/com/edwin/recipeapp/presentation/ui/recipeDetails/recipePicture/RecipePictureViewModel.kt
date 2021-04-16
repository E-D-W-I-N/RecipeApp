package com.edwin.recipeapp.presentation.ui.recipeDetails.recipePicture

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RecipePictureViewModel(state: SavedStateHandle) : ViewModel() {
    val imageUrl = state.get<String>("imageUrl")
}