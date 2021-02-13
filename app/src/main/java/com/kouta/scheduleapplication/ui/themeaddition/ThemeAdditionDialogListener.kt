package com.kouta.scheduleapplication.ui.themeaddition

import android.os.Parcelable

interface ThemeAdditionDialogListener: Parcelable {
    fun onPositiveButtonClick(theme: String)
}