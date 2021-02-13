package com.kouta.scheduleapplication.ui.schedule

import android.os.Parcelable

interface ThemeAdditionDialogListener: Parcelable {
    fun onPositiveButtonClick(theme: String)
}