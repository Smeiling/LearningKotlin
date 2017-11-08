package com.sml.learningkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_0.view.*

/**
 * Created by Smeiling on 2017/11/8.
 */
class FragmentA : Fragment() {

    companion object {
        private val TAG = FragmentA.javaClass.simpleName

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.layout_0, container, false)
        view.tv_id.text = "FragmentA"
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}