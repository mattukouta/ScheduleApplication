package com.kouta.scheduleapplication.util

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
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

@SuppressLint("SetTextI18n")
@BindingAdapter("android:setDeadlineText")
fun setDeadlineText(view: TextView, timestamp: Schedule.TimeStamp?) {
    if (timestamp != null) {
        if (timestamp.date == null) {
            view.text = "あと${timestamp.time?.hour}時間${timestamp.time?.minute}分"
        } else {
            view.text = "あと${timestamp.date.day}日${timestamp.time?.hour}時間"
        }
        val color = ContextCompat.getColor(view.context, R.color.black)
        view.setTextColor(color)
    } else {
        view.text = "予定の期限を過ぎています。"
        val color = ContextCompat.getColor(view.context, R.color.md_red_100)
        view.setTextColor(color)
    }
}