package com.sml.learningkotlin.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sml.learningkotlin.R

/**
 * Created by Smeiling on 2017/12/6.
 */
class WeatherDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.dialog_weather, container, false)
        return view
    }
}