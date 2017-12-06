package com.sml.learningkotlin.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.sml.learningkotlin.R
import com.sml.learningkotlin.adapter.CardViewAdapter
import com.sml.learningkotlin.model.NoteModel
import com.sml.learningkotlin.utils.Utils
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.common_title_bar.view.*
import java.util.*

//java-extend,implements => kotlin-:,
class CardActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var mCurrentCheckedRadioLeft = 0f
    private var tabWidth = 0
    private var btnList: MutableList<RadioButton> = mutableListOf()

    private var year = 2017
    private var month = 11
    private var date = 5
    //星期，默认周日
    private var week = 1

    private var todayDate: String = ""
    private var startDate: Long = 0
    private var endDate: Long = 0

    companion object {
        val TAG = CardActivity.javaClass.simpleName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        initDate()
        initTitleBar()
        initTabBar()
        initListener()
        requestData()
        btn1.isChecked = true
        btn1.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        view_pager.currentItem = week
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft()
    }

    private fun initTabBar() {
        btnList.add(btn1)
        btnList.add(btn2)
        btnList.add(btn3)
        btnList.add(btn4)
        btnList.add(btn5)
        btnList.add(btn6)
        btnList.add(btn7)

        for (i in 1..7) {
            btnList[i - 1].text = (date + i - week).toString()
        }
        startDate = Utils.getTimestampFromDate(year.toString() + "-" + month + "-" + btn1.text.toString(), "yyyy-MM-dd")
        endDate = Utils.getTimestampFromDate(year.toString() + "-" + month.toString() + "-" + btn7.text.toString(), "yyyy-MM-dd")

        initTabWidth()
    }

    private fun initDate() {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+8:00")
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        date = calendar.get(Calendar.DATE)
        week = calendar.get(Calendar.DAY_OF_WEEK)
        todayDate = year.toString() + "-" + month.toString() + "-" + date.toString()
    }

    private fun initTitleBar() {
        var monthes = resources.getStringArray(R.array.ChineseMonth)
        title_bar.tv_title.text = "你好，" + monthes[month - 1]
        title_bar.iv_right.setImageResource(R.mipmap.icon_timeline)
        title_bar.iv_right.setOnClickListener({
            startActivity(Intent(CardActivity@ this, TimeLineActivity::class.java))
        })
        title_bar.iv_left.setOnClickListener({
            var intent = Intent(CardActivity@ this, EditNoteActivity::class.java)
            intent.putExtra("edit_date", getCurDate(view_pager.currentItem))
            startActivity(intent)
        })
    }

    private fun initTabWidth() {
        var wm: WindowManager = CardActivity@ this.windowManager
        var windowWidth = wm.defaultDisplay.width
        var windowHeight = wm.defaultDisplay.height

        var w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        var h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        btn1.measure(w, h)


        var params: ViewGroup.LayoutParams = img1.layoutParams
        Log.d(TAG, "btn1.measuredWidth = " + btn1.measuredWidth)
//        tabWidth = btn1.measuredWidth * 2
        tabWidth = windowWidth / 7
        params.width = tabWidth
        img1.layoutParams = params
    }

    private fun requestData() {

        var avQuery = AVQuery<AVObject>("NoteModel")
        avQuery.orderByAscending("timestamp")
        avQuery.whereGreaterThanOrEqualTo("timestamp", startDate)
        avQuery.whereLessThanOrEqualTo("timestamp", endDate)
        avQuery.findInBackground(object : FindCallback<AVObject>() {
            override fun done(p0: MutableList<AVObject>?, p1: AVException?) {
                if (p1 == null) {
                    var noteMap = mutableMapOf<Long, NoteModel>()
                    p0!!.forEach {
                        val note = NoteModel(it.getString("title"), it.getString("content"), it.getString("date"))
                        noteMap.put(note.timestamp, note)
                    }

                    updateContentView(noteMap)
                } else {
                    Toast.makeText(baseContext, p1?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    private fun updateContentView(noteList: MutableMap<Long, NoteModel>) {
        var notes: MutableList<NoteModel> = mutableListOf()
        (1..7)
                .map { (date + it - week).toString() }
                .map { Utils.getTimestampFromDate(year.toString() + "-" + month + "-" + it, "yyyy-MM-dd") }
                .mapTo(notes) { noteList[it] ?: NoteModel() }

        var adapter = CardViewAdapter(baseContext, notes!!)
        view_pager.adapter = adapter
        view_pager.setCurrentItem(1, true)
//        view_pager.setOnClickListener({
//            var intent = Intent(CardActivity@ this, EditNoteActivity::class.java)
//            intent.putExtra("edit_date", getCurDate(view_pager.currentItem))
//            startActivity(intent)
//        })
    }


    private fun initListener() {
        //java-this => kotlin-ClassName@this
        radioGroup.setOnCheckedChangeListener(CardActivity@ this)
        view_pager.overScrollMode = ViewPager.OVER_SCROLL_NEVER
        view_pager.setOnPageChangeListener(MyPagerOnPageChangeListener())
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        //java-ClassName varName -> kotlin-var varName

        Log.i(TAG, "checkedId = " + checkedId)

        when (checkedId) {
            R.id.btn1 -> view_pager.currentItem = 1
            R.id.btn2 -> view_pager.currentItem = 2
            R.id.btn3 -> view_pager.currentItem = 3
            R.id.btn4 -> view_pager.currentItem = 4
            R.id.btn5 -> view_pager.currentItem = 5
            R.id.btn6 -> view_pager.currentItem = 6
            R.id.btn7 -> view_pager.currentItem = 7
        }

        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft()
        highlightTab(view_pager.currentItem)
    }

    private fun getCurDate(index: Int): String {
        return year.toString() + "-" + month + "-" + (date + index - week)
    }

    private fun highlightTab(index: Int) {
        for (i in 1..7) {
            if (i == index) {
                btnList[i - 1].typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            } else {
                btnList[i - 1].typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            }
        }

    }


    //java-private returnValue funcName => kotlin-private fun funcName:returnValue
    private fun getCurrentCheckedRadioLeft(): Float {
        if (btn1.isChecked) {
            return 0f
        } else if (btn2.isChecked) {
            return tabWidth.toFloat()
        } else if (btn3.isChecked) {
            return tabWidth.toFloat() * 2
        } else if (btn4.isChecked) {
            return tabWidth.toFloat() * 3
        } else if (btn5.isChecked) {
            return tabWidth.toFloat() * 4
        } else if (btn6.isChecked) {
            return tabWidth.toFloat() * 5
        } else if (btn7.isChecked) {
            return tabWidth.toFloat() * 6
        }
        return 0f
    }


    inner class MyPagerOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            //java-switch => kotlin-when
            when (position) {
                0 -> view_pager.currentItem = 1
                1 -> btn1.performClick()
                2 -> btn2.performClick()
                3 -> btn3.performClick()
                4 -> btn4.performClick()
                5 -> btn5.performClick()
                6 -> btn6.performClick()
                7 -> btn7.performClick()
                8 -> view_pager.currentItem = 7
            }
        }
    }


}

