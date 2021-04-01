package com.edwin.recipeapp.presentation.ui.recipesList

import androidx.lifecycle.*
import com.edwin.recipeapp.data.domain.Recipe
import com.edwin.recipeapp.data.repository.RecipeRepository
import com.edwin.recipeapp.util.PreferencesManager
import com.edwin.recipeapp.util.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class RecipeListViewModel @Inject constructor(
        repository: RecipeRepository,
        private val preferencesManager: PreferencesManager,
        state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    private val preferencesFlow = preferencesManager.readFromDataStore
    private val recipeEventChannel = Channel<RecipeListEvents>()
    val recipesEvent = recipeEventChannel.receiveAsFlow()

    val recipes = combine(
            searchQuery.asFlow(),
            preferencesFlow
    ) { query, sortOrder ->
        Pair(query, sortOrder)
    }.flatMapLatest { (query, sortOrder) ->
        repository.getRecipes(query, sortOrder)
    }.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onRecipeSelected(recipe: Recipe) = viewModelScope.launch {
        recipeEventChannel.send(RecipeListEvents.NavigateToRecipeDetailScreen(recipe))
    }

    sealed class RecipeListEvents {
        data class NavigateToRecipeDetailScreen(val recipe: Recipe) : RecipeListEvents()
    }
}