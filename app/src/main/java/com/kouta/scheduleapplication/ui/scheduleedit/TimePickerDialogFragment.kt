package com.kouta.scheduleapplication.ui.scheduleedit

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Date

class TimePickerDialogFragment(
    private val listener: TimePickerDialogListener
): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time

        val context = context
        return when {
            context != null -> {
                TimePickerDialog(
                    context,
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true)
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }
    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        listener.selectedTime(hour, minute)
    }
}