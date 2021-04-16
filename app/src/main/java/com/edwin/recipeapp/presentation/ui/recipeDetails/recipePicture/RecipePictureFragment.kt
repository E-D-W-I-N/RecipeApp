package com.edwin.recipeapp.presentation.ui.recipeDetails.recipePicture

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.edwin.recipeapp.R
import com.edwin.recipeapp.databinding.RecipePicuterFragmentBinding

class RecipePictureFragment : Fragment(R.layout.recipe_picuter_fragment) {

    private val viewModel: RecipePictureViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipePicuterFragmentBinding.bind(view)
        Glide.with(view)
            .load(viewModel.imageUrl)
            .into(binding.recipePicture)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_recipe_picture, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}