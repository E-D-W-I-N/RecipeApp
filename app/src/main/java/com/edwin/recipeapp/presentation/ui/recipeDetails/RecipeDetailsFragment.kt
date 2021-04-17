package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.edwin.recipeapp.R
import com.edwin.recipeapp.databinding.RecipeDetailsFragmentBinding
import com.edwin.recipeapp.domain.RecipeBrief
import com.edwin.recipeapp.presentation.ui.util.OnItemClickListener
import com.edwin.recipeapp.util.Resource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.recipe_details_fragment) {
    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipeDetailsFragmentBinding.bind(view)

        val onPictureClick = OnItemClickListener<String> { viewModel.onRecipePictureSelected(it) }
        val onRecommendedClick = OnItemClickListener<RecipeBrief> {
            viewModel.onRecommendedRecipeSelected(it.uuid)
        }
        val viewPagerAdapterPicture = ViewPagerAdapterPicture(onPictureClick)
        val viewPagerAdapterRecommended = ViewPagerAdapterRecommended(onRecommendedClick)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.apply {
            viewPagerPictures.adapter = viewPagerAdapterPicture
            TabLayoutMediator(pagerTabLayout, viewPagerPictures) { _, _ -> }.attach()

            viewPagerRecommended.adapter = viewPagerAdapterRecommended
            viewPagerRecommended.offscreenPageLimit = 3
            viewPagerRecommended.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            viewPagerRecommended.setPageTransformer(compositePageTransformer)

            viewModel.recipe?.observe(viewLifecycleOwner, { result ->
                viewPagerAdapterPicture.submitList(result.data?.images)

                detailTextName.text = result.data?.name
                val date = result.data?.lastUpdated ?: 0
                detailTextDate.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        .format(Date(date * 1000))
                detailTextDescription.text = result.data?.description
                detailDifficultyBar.rating = result.data?.difficulty?.toFloat() ?: 0.0f
                detailTextInstructions.text = result.data?.instructions

                viewPagerAdapterRecommended.submitList(result.data?.similar)

                binding.progressBar.isVisible = result is Resource.Loading && result.data == null
                binding.textViewError.isVisible = result is Resource.Error && result.data == null
                binding.textViewError.text = result.error?.localizedMessage
                binding.scrollView.isVisible = result.data?.name?.isNotEmpty() == true
            })
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recipesEvent.collect { event ->
                when (event) {
                    is RecipeDetailsViewModel.RecipeDetailsEvents.NavigateToRecommendedRecipe -> {
                        val action =
                                RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentSelf(
                                        event.uuid
                                )
                        findNavController().navigate(action)
                    }
                    is RecipeDetailsViewModel.RecipeDetailsEvents.NavigateToRecipePicture -> {
                        val action =
                                RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipePictureFragment(
                                        event.imageUrl
                                )
                        findNavController().navigate(action)
                    }
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_recipe_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}