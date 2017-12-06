package com.sml.learningkotlin.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.sml.learningkotlin.R
import com.sml.learningkotlin.model.NoteModel
import kotlinx.android.synthetic.main.item_card_view.view.*

/**
 * Created by Smeiling on 2017/11/9.
 */
class CardViewAdapter(private var context: Context, private var noteList: List<NoteModel>) : PagerAdapter() {

    public fun getItems(): List<NoteModel> {
        return noteList
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        //Layout
        var layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        var frameLayout = FrameLayout(context)
        layoutParams.gravity = Gravity.CENTER
        frameLayout.layoutParams = layoutParams
        frameLayout.setBackgroundColor(Color.WHITE)
        Log.d("sml", noteList.size.toString())

        if (position == 0 || position == noteList.size + 1) {
            //空白页
        } else {
            var view = LayoutInflater.from(context).inflate(R.layout.item_card_view, null)

            view.tv_title.text = noteList[position - 1].title ?: ""
            view.tv_content.text = noteList[position - 1].content ?: ""
            view.tv_date.text = noteList[position - 1].date ?: ""
            frameLayout.addView(view)
        }
        container!!.addView(frameLayout)
        return frameLayout
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        //前后各加一空白页面
        return noteList.size + 2
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View?)
    }

}