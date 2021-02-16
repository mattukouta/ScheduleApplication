package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.kouta.scheduleapplication.model.Schedule.TimeStamp.Date

class DatePickerDialogFragment(
    private val listener: DatePickerDialogListener,
    private val date: Date
): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        return when {
            context != null -> {
                DatePickerDialog(
                    context,
                    this,
                    date.year,
                    date.month - 1,
                    date.day
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