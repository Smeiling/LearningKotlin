package com.sml.learningkotlin.utils

import android.content.Context
import com.sml.learningkotlin.activity.CardActivity
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


}