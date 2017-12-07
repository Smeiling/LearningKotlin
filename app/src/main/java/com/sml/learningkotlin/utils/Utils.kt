package com.sml.learningkotlin.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import com.sml.learningkotlin.activity.CardActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Smeiling on 2017/12/5.
 */
object Utils {

    var markData: HashMap<String, String> = HashMap()

    fun dpi2px(context: Context, dpi: Float): Int {
        return (context.resources.displayMetrics.density * dpi + 0.5f).toInt()
    }


    /**
     * 得到标记日期数据，可以通过该数据得到标记日期的信息，开发者可自定义格式
     * 目前HashMap<String></String>, String>的组成仅仅是为了DEMO效果
     *
     * @return HashMap<String></String>, String> 标记日期数据
     */
    fun loadMarkData(): java.util.HashMap<String, String> {
        return markData
    }


    /**
     * 时间戳转为日期
     */
    fun getDateFromTimestamp(timestamp: Long, format: String): String {
        if (timestamp == 0L) {
            return ""
        }
        val format = SimpleDateFormat(format)
        return format.format(timestamp)
    }

    /**
     * 日期转为时间戳
     */
    fun getTimestampFromDate(date: String, format: String): Long {
        val format = SimpleDateFormat(format)
        return format.parse(date).time
    }

    /**
     * 获取屏幕宽度
     */
    fun getWindowWidth(activity: Activity): Int {
        var wm: WindowManager = activity.windowManager
        return wm.defaultDisplay.width
    }

    /**
     * 获取屏幕高度
     */
    fun getWindowHeight(activity: Activity): Int {
        var wm: WindowManager = activity.windowManager
        return wm.defaultDisplay.height
    }
}