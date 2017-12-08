package com.sml.learningkotlin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.ldf.calendar.component.CalendarAttr
import com.ldf.calendar.component.CalendarViewAdapter
import com.ldf.calendar.interf.OnSelectDateListener
import com.ldf.calendar.model.CalendarDate
import com.ldf.calendar.view.Calendar
import com.ldf.calendar.view.MonthPager
import com.sml.learningkotlin.R
import com.sml.learningkotlin.view.CustomDayView
import kotlinx.android.synthetic.main.dialog_calendar.*
import kotlinx.android.synthetic.main.dialog_calendar.view.*

/**
 * Created by Smeiling on 2017/12/5.
 */
class CalendarDialog : DialogFragment() {

    lateinit var currentDate: CalendarDate
    lateinit var calendarAdapter: CalendarViewAdapter
    lateinit var selectDateListener: OnSelectDateListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = inflater?.inflate(R.layout.dialog_calendar, container, false)
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        view!!.btn_back!!.setOnClickListener({
            view!!.calendar_view!!.currentItem = view!!.calendar_view.currentPosition + 1
        })
        view!!.btn_forward!!.setOnClickListener({
            view!!.calendar_view!!.currentItem = view!!.calendar_view.currentPosition - 1
        })

        var customDayView = CustomDayView(activity, R.layout.custom_day)
        calendarAdapter = CalendarViewAdapter(activity, selectDateListener, CalendarAttr.CalendayType.MONTH, customDayView)
        CalendarViewAdapter.weekArrayType = 1
        initCurrentDate()
        initMonthPager()
    }

    private fun initMonthPager() {
        view!!.calendar_view.adapter = calendarAdapter
        view!!.calendar_view.currentItem = MonthPager.CURRENT_DAY_INDEX
        view!!.calendar_view.setPageTransformer(false) { page, position ->
            var pos = Math.sqrt((1 - Math.abs(position)).toDouble()).toFloat()
            page!!.alpha = pos
        }
        view!!.calendar_view.addOnPageChangeListener(object : MonthPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                var currentCalendars = calendarAdapter.pagers
                if (currentCalendars[p0 % currentCalendars.size] is Calendar) {
                    var date = currentCalendars[p0 % currentCalendars.size].seedDate
                    currentDate = date
                    show_year_month.text = date.year.toString() + "年" + date.month.toString() + "月"
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }
        })
    }

    private fun initCurrentDate() {
        currentDate = CalendarDate()
        show_year_month.text = currentDate.year.toString() + "年" + currentDate.month.toString() + "月"
    }
}