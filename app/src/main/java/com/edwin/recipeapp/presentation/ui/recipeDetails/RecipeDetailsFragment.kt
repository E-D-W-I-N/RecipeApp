package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import com.edwin.recipeapp.R
import com.edwin.recipeapp.data.util.Resource
import com.edwin.recipeapp.databinding.RecipeDetailsFragmentBinding
import com.edwin.recipeapp.domain.RecipeBrief
import com.edwin.recipeapp.presentation.util.OnItemClickListener
import com.edwin.recipeapp.presentation.util.showToast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.recipe_details_fragment) {
    private val viewModel: RecipeDetailsViewModel by viewModels()

    private val onPictureClick = OnItemClickListener<String> { imageUrl ->
        val action = RecipeDetailsFragmentDirections
            .actionRecipeDetailsFragmentToRecipePictureFragment(imageUrl)
        findNavController().navigate(action)
    }
    private val onRecommendedClick = OnItemClickListener<RecipeBrief> { recipeBrief ->
        val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentSelf(
            recipeBrief.uuid
        )
        findNavController().navigate(action)
    }
    private val recipePictureAdapter = RecipePictureAdapter(onPictureClick)
    private val recipeRecommendedAdapter = RecipeRecommendedAdapter(onRecommendedClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipeDetailsFragmentBinding.bind(view)
        setupViewPagers(binding)
        bindData(binding)
        setHasOptionsMenu(true)
    }

    private fun setupViewPagers(binding: RecipeDetailsFragmentBinding) = with(binding) {
        viewPagerPictures.adapter = recipePictureAdapter
        TabLayoutMediator(pagerTabLayout, viewPagerPictures) { _, _ -> }.attach()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page, position ->
            val shrinkPercent = 0.25f
            page.scaleY = 1 - (shrinkPercent * abs(position))
        }
        val pageLimit = 5
        viewPagerRecommended.apply {
            adapter = recipeRecommendedAdapter
            offscreenPageLimit = pageLimit
            setPageTransformer(compositePageTransformer)
        }
    }

    private fun bindData(binding: RecipeDetailsFragmentBinding) = with(binding) {
        val defaultDifficulty = 0.0f
        viewModel.recipe?.observe(viewLifecycleOwner, { result ->
            val data = result.data
            recipePictureAdapter.submitList(data?.images)
            recipeRecommendedAdapter.submitList(data?.similar)

            detailTextName.text = data?.name
            detailTextDate.text = data?.updateDate
            detailTextDescription.text = data?.description
            detailDifficultyBar.rating = data?.difficulty ?: defaultDifficulty
            detailTextInstructions.text = data?.instructions

            progressBar.isVisible = result is Resource.Loading && result.data == null
            textViewError.isVisible = result is Resource.Error && result.data == null
            textViewError.text = result.error?.localizedMessage
            scrollView.isVisible = data?.name?.isNotEmpty() == true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_recipe_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                showToast(getString(R.string.favorites_toast_text), Toast.LENGTH_LONG)
                true
            }
            R.id.action_share -> {
                showToast(getString(R.string.share_toast_text), Toast.LENGTH_LONG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}