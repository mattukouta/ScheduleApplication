package com.kouta.scheduleapplication.util

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.model.Schedule

@BindingAdapter("android:setItemSelected")
fun setItemSelected(view: Spinner, position: Int) {
    if (view.id == (R.id.spinner_theme)) Log.d("checkPoosition", position.toString())
    view.setSelection(position)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("android:setTimeText")
fun setTimeText(view: TextView, time: Schedule.TimeStamp.Time?) {
    view.text = "${time?.hour}:%0,2d".format(time?.minute)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("android:setDateText")
fun setDateText(view: TextView, date: Schedule.TimeStamp.Date?) {
    view.text = "${date?.year}年${date?.month}月${date?.day}日(${date?.dayOfWeek})"
}