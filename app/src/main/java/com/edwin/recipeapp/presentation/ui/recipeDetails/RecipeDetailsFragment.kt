package com.edwin.recipeapp.presentation.ui.recipeDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.edwin.recipeapp.R
import com.edwin.recipeapp.databinding.RecipeDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.recipe_details_fragment) {

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipeDetailsFragmentBinding.bind(view)

        binding.apply {

            viewModel.recipeDetails?.observe(viewLifecycleOwner, { result ->
                viewPager2.adapter = result.data?.images?.let { ViewPagerAdapter(it) }
                viewPager2.offscreenPageLimit = 3
                viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(30))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - abs(position)
                    page.scaleY = 0.85f + r * 0.25f
                }
                viewPager2.setPageTransformer(compositePageTransformer)

                detailTextName.text = result.data?.name
                detailTextDescription.text = result.data?.description
                detailTextInstructions.text = result.data?.instructions
            })
        }
    }
}