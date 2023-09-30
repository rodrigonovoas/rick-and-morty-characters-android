package com.rodrigonovoa.rickandmortycharacters.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rodrigonovoa.rickandmortycharacters.R

object DialogUtils {
    fun showErrorDialog(context: Context) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
            .setTitle("Error retrieving data")
            .setPositiveButton("OK") { dialog, which -> }
            .show()
    }
}