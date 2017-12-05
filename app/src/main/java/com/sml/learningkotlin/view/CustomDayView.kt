package com.sml.learningkotlin.view

import android.content.Context
import android.graphics.Color
import android.view.View
import com.ldf.calendar.component.State
import com.ldf.calendar.interf.IDayRenderer
import com.ldf.calendar.model.CalendarDate
import com.ldf.calendar.view.DayView
import kotlinx.android.synthetic.main.custom_day.view.*

/**
 * Created by Smeiling on 2017/12/5.
 */
class CustomDayView(context: Context, layoutResource: Int) : DayView(context, layoutResource) {

    val today = CalendarDate()

    override fun refreshContent() {
        renderToday(day.date)
        renderSelect(day.state)
        renderMarker(day.date, day.state)
        super.refreshContent()
    }

    private fun renderMarker(date: CalendarDate, state: State) {
        iv_maker.visibility = View.GONE
    }

    private fun renderSelect(state: State) {
        if (state == State.SELECT) {
            selected_background.visibility = View.VISIBLE
            tv_date.setTextColor(Color.WHITE)
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            selected_background.visibility = View.GONE
            tv_date.setTextColor(Color.parseColor("#d5d5d5"))
        } else {
            selected_background.visibility = View.GONE
            tv_date.setTextColor(Color.parseColor("#111111"))
        }
    }

    private fun renderToday(date: CalendarDate) {
        if (date!!.equals(today)) {
            //tv_date.text = "今"

            tv_date.text = date.day.toString()
            today_background.visibility = View.VISIBLE
        } else {
            tv_date.text = date.day.toString()
            today_background.visibility = View.GONE
        }
    }

    override fun copy(): IDayRenderer {
        return CustomDayView(context, layoutResource)
    }


}