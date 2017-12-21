package com.sml.learningkotlin.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.sml.learningkotlin.R
import com.sml.learningkotlin.model.CardModel
import com.sml.learningkotlin.utils.Utils
import kotlinx.android.synthetic.main.item_card_view.view.*

/**
 * Created by Smeiling on 2017/11/9.
 */
class CardViewAdapter(private var context: Context, private var cardList: List<CardModel>) : PagerAdapter() {

    lateinit var pageClickListener: View.OnClickListener

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        //Layout
        var layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        var frameLayout = FrameLayout(context)
        layoutParams.gravity = Gravity.CENTER
        frameLayout.layoutParams = layoutParams
        frameLayout.setBackgroundColor(Color.WHITE)

        frameLayout.setOnClickListener({
            pageClickListener?.onClick(it)
        })
        Log.d("sml", cardList.size.toString())

        if (position == 0 || position == cardList.size + 1) {
            //blank
        } else {
            frameLayout.tag = cardList[position - 1].timestamp
            var view = LayoutInflater.from(context).inflate(R.layout.item_card_view, null)
            view.main_card.setBackgroundColor(Color.parseColor(cardList[position - 1].cardType.rgb))
            if (TextUtils.isEmpty(cardList[position - 1].title)) {
                view.empty_view.visibility = View.VISIBLE
                view.content_view.visibility = View.GONE
            } else {
                view.empty_view.visibility = View.GONE
                view.content_view.visibility = View.VISIBLE
                view.tv_title.text = cardList[position - 1].title ?: ""
                view.tv_content.text = cardList[position - 1].content ?: ""
                view.tv_date.text = Utils.getDateFromTimestamp(cardList[position - 1].timestamp, "yyyy-MM-dd")
            }
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
        return cardList.size + 2
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View?)
    }

    public fun setOnPageClickListener(listener: View.OnClickListener) {
        pageClickListener = listener
    }

    public fun updateItems(cardList: List<CardModel>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

}