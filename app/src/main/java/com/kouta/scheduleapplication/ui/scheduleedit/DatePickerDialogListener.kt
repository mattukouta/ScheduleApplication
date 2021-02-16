package com.kouta.scheduleapplication.ui.scheduleedit

interface DatePickerDialogListener {
    fun selectedDate(year: Int, month: Int, day: Int, dayOfWeek: Int)
}