package com.edwin.recipeapp.presentation.ui.recipesList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.edwin.recipeapp.data.repository.RecipeRepository
import com.edwin.recipeapp.data.util.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class RecipeListViewModel @Inject constructor(
    repository: RecipeRepository,
    preferencesManager: PreferencesManager,
    state: SavedStateHandle
) : ViewModel() {
    val searchQuery = state.getLiveData("searchQuery", "")
    private val preferencesFlow = preferencesManager.readFromDataStore

    val recipes = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, sortOrder ->
        Pair(query, sortOrder)
    }.flatMapLatest { (query, sortOrder) ->
        repository.getRecipes(query, sortOrder)
    }.asLiveData()
}