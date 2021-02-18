package com.kouta.scheduleapplication.ui.schedule

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.Animation.DEGREE
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.Animation.DURATION

object FloatingAction {
    object Animation {
        const val DURATION = 200L
        const val DEGREE = 135F
    }

    fun floatingActionButtonAnimation(
        floatingActionButton: FloatingActionButton,
        isShow: Boolean,
        floatingActionButtonViews: List<View>
    ) {
        floatingActionButton
            .animate()
            .setDuration(DURATION)
            .rotation(if(isShow) 0f else DEGREE)

        when(isShow) {
            true -> allFloatingActionButtonHide(floatingActionButtonViews)
            false -> allFloatingActionButtonShow(floatingActionButtonViews)
        }
    }

    private fun allFloatingActionButtonShow(floatingActionButtonViews: List<View>) {
        floatingActionButtonViews.forEach {
            floatActionButtonShow(it)
        }
    }

    private fun floatActionButtonShow(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 0f
            translationY = 120f
            animate()
                .setDuration(DURATION)
                .translationY(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }
    }

    fun allFloatingActionButtonHide(floatingActionButtonViews: List<View>) {
        floatingActionButtonViews.forEach {
            floatActionButtonHide(it)
        }
    }

    private fun floatActionButtonHide(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 1f
            translationY = 0f
            animate()
                .setDuration(DURATION)
                .translationY(view.height.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                        super.onAnimationEnd(animation)
                    }
                }).alpha(0f)
                .start()
        }
    }
}