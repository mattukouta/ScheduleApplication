package com.kouta.scheduleapplication.ui.scheduleedit

interface TimePickerDialogListener {
    fun selectedTime(hourOfDay: Int, minute: Int)
}