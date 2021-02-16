package com.kouta.scheduleapplication.ui.scheduleedit

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.kouta.scheduleapplication.model.Schedule.TimeStamp.Time

class TimePickerDialogFragment(
    private val listener: TimePickerDialogListener,
    private val time: Time
): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        return when {
            context != null -> {
                TimePickerDialog(
                    context,
                    this,
                    time.hour,
                    time.minute,
                    true)
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }
    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        listener.selectedTime(hour, minute)
    }
}