package com.edwin.recipeapp.presentation.ui.recipesList.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.edwin.recipeapp.R
import com.edwin.recipeapp.databinding.BottomSheetFragmentBinding
import com.edwin.recipeapp.util.SortOrder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: BottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = BottomSheetFragmentBinding.bind(view)

        binding.apply {
            sortByName.setOnClickListener {
                viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                dismiss()
            }
            sortByDate.setOnClickListener {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                dismiss()
            }
        }
    }
}