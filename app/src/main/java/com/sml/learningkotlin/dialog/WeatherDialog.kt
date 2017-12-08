package com.sml.learningkotlin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sml.learningkotlin.R

/**
 * Created by Smeiling on 2017/12/6.
 */
class WeatherDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var displayMetrics = DisplayMetrics()
        //dialog.window.setLayout((displayMetrics.widthPixels * 0.8).toInt(), (displayMetrics.widthPixels * 0.8).toInt())
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater!!.inflate(R.layout.dialog_weather, container, false)
        return view
    }
}