package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Date

class DatePickerDialogFragment(
    private val listener: DatePickerDialogListener
): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time

        val context = context
        return when {
            context != null -> {
                DatePickerDialog(
                    context,
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
                )
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dayOfWeek = getDayOfWeek(
            year,
            month,
            dayOfMonth
        )

        listener.selectedDate(year, month + 1, dayOfMonth, dayOfWeek)
    }

    private fun getDayOfWeek(
        year: Int,
        month: Int,
        dayOfMonth: Int
    ): Int {
        val calendar = Calendar.getInstance()
        calendar.set(
            year,
            month,
            dayOfMonth
        )

        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }
}