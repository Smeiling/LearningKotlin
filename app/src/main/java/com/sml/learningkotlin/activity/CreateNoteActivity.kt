package com.sml.learningkotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.SaveCallback
import com.ldf.calendar.interf.OnSelectDateListener
import com.ldf.calendar.model.CalendarDate
import com.sml.learningkotlin.R
import com.sml.learningkotlin.fragment.CalendarDialog
import com.sml.learningkotlin.fragment.WeatherDialog
import com.sml.learningkotlin.model.NoteModel
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.common_title_bar.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Smeiling on 2017/11/15.
 */
class CreateNoteActivity : AppCompatActivity() {

    var todayDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        initTitleBar()
        initBottomBar()
    }

    private fun initBottomBar() {
        iv_calendar.setOnClickListener({
            var dialog = CalendarDialog()
            dialog.selectDateListener = object : OnSelectDateListener {
                override fun onSelectDate(p0: CalendarDate?) {
                    todayDate = p0.toString()
                    title_bar.tv_title.text = todayDate
                    dialog.dismissAllowingStateLoss()
                }

                override fun onSelectOtherMonth(p0: Int) {

                }
            }
            dialog.show(supportFragmentManager, "calendar_dialog")
        })

        iv_weather.setOnClickListener({
            var dialog = WeatherDialog()
            dialog.show(supportFragmentManager, "weather_dialog")
        })
    }

    private fun initTitleBar() {
        todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        title_bar.tv_title.text = todayDate
        title_bar.iv_left.setImageResource(R.mipmap.icon_back)
        title_bar.iv_left.setOnClickListener({
            finish()
        })
        title_bar.iv_right.setImageResource(R.mipmap.icon_check)
        title_bar.iv_right.setOnClickListener({
            if (!TextUtils.isEmpty(et_title.text) && !TextUtils.isEmpty(et_content.text)) {
                var note = NoteModel(et_title.text.toString(), et_content.text.toString(), todayDate.replace("-", ""))
                saveNote(note)
            }
        })
    }

    private fun saveNote(noteModel: NoteModel) {
        var note = AVObject("NoteModel")
        note.put("title", noteModel.title)
        note.put("content", noteModel.content)
        note.put("date", noteModel.date)
        note.put("timestamp", noteModel.timestamp)
        note.saveInBackground(object : SaveCallback() {
            override fun done(p0: AVException?) {
                if (p0 == null) {
                    Toast.makeText(baseContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(baseContext, p0.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}