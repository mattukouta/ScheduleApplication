package com.kouta.scheduleapplication.ui.themeaddition

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentThemeAdditionDialogBinding
import com.kouta.scheduleapplication.util.autoCleared

class ThemeAdditionDialogFragment : DialogFragment() {
    private var binding: FragmentThemeAdditionDialogBinding by autoCleared()
    private val args: ThemeAdditionDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentThemeAdditionDialogBinding
            .inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAppTheme_AlertDialog)
            .setView(binding.root)
            .setMessage(R.string.dialog_theme_addition_message)
            .setPositiveButton(R.string.dialog_theme_addition_positive_button, null)
            .setNegativeButton(R.string.dialog_theme_addition_negative_button) { _, _ -> }
            .setCancelable(false)

        return alertDialog.create()
    }

    override fun onResume() {
        super.onResume()

        val alertDialog = dialog as AlertDialog
        val positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            val inputTheme = (binding.editTextTheme.text?.toString() ?: "").trim()

            if (inputTheme.isNotBlank()) {
                args.listener.onPositiveButtonClick(inputTheme)
                dismiss()
            } else {
                binding.textViewWarning.visibility = View.VISIBLE
            }
        }
    }
}