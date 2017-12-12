package com.sml.learningkotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.sml.learningkotlin.R
import com.sml.learningkotlin.adapter.TimeViewAdapter
import com.sml.learningkotlin.model.NoteModel
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.common_title_bar.view.*

class TimeLineActivity : AppCompatActivity() {

    private lateinit var adapter: TimeViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)
        initTitleBar()
        initContentView()
        requestData()
    }

    private fun requestData() {
        var query = AVQuery<AVObject>("NoteModel")
        query.orderByDescending("timestamp")
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(p0: MutableList<AVObject>?, p1: AVException?) {
                var list = mutableListOf<NoteModel>()
                p0?.forEach {
                    list.add(NoteModel(it.getString("title"), it.getString("content"), it.getString("date")))
                }
                updateContent(list)
            }
        })
    }

    private fun updateContent(list: MutableList<NoteModel>) {
        adapter.updateData(list)
    }

    private fun initContentView() {
        time_list.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        adapter = TimeViewAdapter(mutableListOf())
        time_list.adapter = adapter
    }

    private fun initTitleBar() {
        title_bar.tv_title.text = "Timeline"
        title_bar.iv_right.visibility = View.INVISIBLE
        title_bar.iv_left.setImageResource(R.mipmap.icon_back)
        title_bar.iv_left.setOnClickListener({
            finish()
        })
    }
}
