package com.sml.learningkotlin.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val view = inflater?.inflate(R.layout.dialog_calendar, container, false)

        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        view!!.btn_back!!.setOnClickListener(View.OnClickListener {
            view!!.calendar_view!!.currentItem = view!!.calendar_view.currentPosition + 1
        })
        view!!.btn_forward!!.setOnClickListener(View.OnClickListener {
            view!!.calendar_view!!.currentItem = view!!.calendar_view.currentPosition - 1
        })

        var customDayView = CustomDayView(activity, R.layout.custom_day)
        calendarAdapter = CalendarViewAdapter(activity, selectDateListener, CalendarAttr.CalendayType.MONTH, customDayView)
        initCurrentDate()
        initMonthPager()
    }

    private fun initMonthPager() {
        view!!.calendar_view.adapter = calendarAdapter
        view!!.calendar_view.currentItem = MonthPager.CURRENT_DAY_INDEX
        view!!.calendar_view.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(page: View?, position: Float) {
                var pos = Math.sqrt((1 - Math.abs(position)).toDouble()).toFloat()
                page!!.alpha = pos
            }
        })
        view!!.calendar_view.addOnPageChangeListener(object : MonthPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                var currentCalendars = calendarAdapter.pagers
                if (currentCalendars[p0 % currentCalendars.size] is Calendar) {
                    var date = currentCalendars[p0 % currentCalendars.size].seedDate
                    currentDate = date
                    show_year_view.text = date.year.toString() + "年"
                    show_month_view.text = date.month.toString()
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }
        })
    }

    private fun initCurrentDate() {
        currentDate = CalendarDate()
        show_year_view.text = currentDate.year.toString() + "年"
        show_month_view.text = currentDate.month.toString()
    }
}