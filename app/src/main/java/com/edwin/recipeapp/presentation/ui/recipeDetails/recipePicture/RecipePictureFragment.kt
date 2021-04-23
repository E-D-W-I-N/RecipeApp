package com.edwin.recipeapp.presentation.ui.recipeDetails.recipePicture

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.edwin.recipeapp.R
import com.edwin.recipeapp.databinding.RecipePicuterFragmentBinding
import com.edwin.recipeapp.presentation.util.loadImage
import com.edwin.recipeapp.presentation.util.saveToGallery
import com.edwin.recipeapp.presentation.util.showToast


class RecipePictureFragment : Fragment(R.layout.recipe_picuter_fragment) {
    private val viewModel: RecipePictureViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast(getString(R.string.permission_granted), Toast.LENGTH_LONG)
            } else {
                showToast(getString(R.string.permission_denied), Toast.LENGTH_LONG)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RecipePicuterFragmentBinding.bind(view)
        binding.recipePicture.loadImage(requireContext(), viewModel.imageUrl)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_recipe_picture, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_download) {
            checkPermission()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                saveImage()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                showDialog()
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun saveImage() {
        Glide.with(this)
            .asBitmap()
            .load(viewModel.imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    resource.saveToGallery(requireContext())
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        showToast(getString(R.string.image_downloaded), Toast.LENGTH_LONG)
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme).apply { }
            .setMessage(getString(R.string.permission_dialog_message))
            .setTitle(getString(R.string.permission_dialog_title))
            .setPositiveButton(getString(R.string.positive_button_text)) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton(getString(R.string.negative_button_text)) { _, _ -> }
            .create().show()
    }
}