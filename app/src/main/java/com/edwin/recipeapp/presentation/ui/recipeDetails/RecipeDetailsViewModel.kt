package com.edwin.recipeapp.presentation.ui.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.edwin.recipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    repository: RecipeRepository,
    state: SavedStateHandle
) : ViewModel() {
    val uuid = state.get<String>("uuid")
    val recipe = uuid?.let { repository.getRecipe(it) }?.asLiveData()
    private val recipeEventChannel = Channel<RecipeDetailsEvents>()
    val recipesEvent = recipeEventChannel.receiveAsFlow()

    fun onRecommendedRecipeSelected(uuid: String) = viewModelScope.launch {
        recipeEventChannel.send(
            RecipeDetailsEvents.NavigateToRecommendedRecipe(uuid)
        )
    }

    fun onRecipePictureSelected(imageUrl: String) = viewModelScope.launch {
        recipeEventChannel.send(
            RecipeDetailsEvents.NavigateToRecipePicture(imageUrl)
        )
    }

    sealed class RecipeDetailsEvents {
        data class NavigateToRecommendedRecipe(val uuid: String) : RecipeDetailsEvents()
        data class NavigateToRecipePicture(val imageUrl: String) : RecipeDetailsEvents()
    }
}