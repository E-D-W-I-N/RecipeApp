package com.edwin.recipeapp.presentation.ui.recipesList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwin.recipeapp.R
import com.edwin.recipeapp.data.util.Resource
import com.edwin.recipeapp.databinding.RecipeListFragmentBinding
import com.edwin.recipeapp.domain.Recipe
import com.edwin.recipeapp.presentation.util.OnItemClickListener
import com.edwin.recipeapp.presentation.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class RecipeListFragment : Fragment(R.layout.recipe_list_fragment) {
    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var searchView: SearchView

    private val onRecipeClick = OnItemClickListener<Recipe> { recipe ->
        val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailsFragment(
            recipe.uuid
        )
        findNavController().navigate(action)
    }
    private val recipeListAdapter = RecipeListAdapter(onRecipeClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipeListFragmentBinding.bind(view)
        setupViewPager(binding)
        bindData(binding)
        setHasOptionsMenu(true)
    }

    private fun setupViewPager(binding: RecipeListFragmentBinding) = with(binding) {
        recyclerViewRecipe.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindData(binding: RecipeListFragmentBinding) = with(binding) {
        viewModel.recipes.observe(viewLifecycleOwner, { result ->
            recipeListAdapter.submitList(result.data)
            progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
            textViewError.text = result.error?.localizedMessage
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_recipe_list, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (!pendingQuery.isNullOrEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }
        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_sort) {
            findNavController().navigate(RecipeListFragmentDirections.actionRecipeListFragmentToBottomSheetFragment())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}