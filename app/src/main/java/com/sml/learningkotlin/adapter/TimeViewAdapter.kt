package com.sml.learningkotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sml.learningkotlin.R
import com.sml.learningkotlin.model.CardModel
import kotlinx.android.synthetic.main.item_timeline_bg.view.*


/**
 * Created by Smeiling on 2017/12/6.
 */
class TimeViewAdapter(private var cardList: MutableList<CardModel>) : RecyclerView.Adapter<TimeViewAdapter.TimeViewHolder>() {

    /**
     * 更新数据
     */
    fun updateData(cards: MutableList<CardModel>) {
        this.cardList = cards
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TimeViewHolder?, position: Int) {
        holder?.contentView?.text = cardList[position]?.content
        holder?.dateView?.text = cardList[position]?.date
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_timeline_bg, null)
        return TimeViewHolder(view)
    }


    inner class TimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contentView = view.time_content
        var dateView = view.time_date
    }
}