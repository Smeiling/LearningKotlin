package com.sml.learningkotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sml.learningkotlin.R
import kotlinx.android.synthetic.main.layout_0.view.*

/**
 * Created by Smeiling on 2017/11/8.
 */
class FragmentB : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.layout_1, null)
        view.tv_id.text = "FragmentB"
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}