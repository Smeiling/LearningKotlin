package com.sml.learningkotlin.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.avos.avoscloud.*
import com.sml.learningkotlin.R
import com.sml.learningkotlin.adapter.CardViewAdapter
import com.sml.learningkotlin.dialog.ShowNoteDialog
import com.sml.learningkotlin.model.NoteModel
import com.sml.learningkotlin.utils.Utils
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.common_title_bar.view.*
import java.util.*

//java-extend,implements => kotlin-:,
class CardActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var mCurrentPageIndex: Int = 1
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

    private lateinit var adapter: CardViewAdapter

    companion object {
        val TAG = CardActivity.javaClass.simpleName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        checkLoginState()
        initDate()
        initTitleBar()
        initTabBar()
    }

    override fun onStart() {
        super.onStart()
        updateCalendarBarValue()
        requestData()
    }

    /**
     * 检测登录状态
     */
    private fun checkLoginState() {
        if (AVUser.getCurrentUser() == null) {
            startActivity(Intent(CardActivity@ this, LoginActivity::class.java))
        } else {
            Toast.makeText(baseContext, "Hello, " + AVUser.getCurrentUser().username, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 获取当前日期用于数据请求及日历内容渲染
     */
    private fun initDate() {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+8:00")
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        date = calendar.get(Calendar.DATE)
        week = calendar.get(Calendar.DAY_OF_WEEK)
        todayDate = year.toString() + "-" + month.toString() + "-" + date.toString()
    }

    /**
     * 初始化标题栏
     */
    private fun initTitleBar() {
        var monthes = resources.getStringArray(R.array.ChineseMonth)
        title_bar.tv_title.text = "你好，" + monthes[month - 1]
        title_bar.iv_right.setImageResource(R.mipmap.icon_timeline)
        title_bar.iv_right.setOnClickListener({
            startActivity(Intent(CardActivity@ this, TimeLineActivity::class.java))
        })
        title_bar.iv_left.setOnClickListener({
            startActivity(Intent(CardActivity@ this, SettingsActivity::class.java))
        })
    }

    /**
     * 初始化日历栏
     */
    private fun initTabBar() {
        radioGroup.setOnCheckedChangeListener(CardActivity@ this)
        btnList.add(btn1)
        btnList.add(btn2)
        btnList.add(btn3)
        btnList.add(btn4)
        btnList.add(btn5)
        btnList.add(btn6)
        btnList.add(btn7)
        initTabWidth()
    }

    private fun initTabWidth() {
        var w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        var h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        btn1.measure(w, h)

        var params: ViewGroup.LayoutParams = img1.layoutParams
        Log.d(TAG, "btn1.measuredWidth = " + btn1.measuredWidth)
        tabWidth = Utils.getWindowWidth(CardActivity@ this) / 7
        params.width = tabWidth
        img1.layoutParams = params
    }

    private fun updateCalendarBarValue() {
        mCurrentPageIndex = week - 1
        for (i in 1..7) {
            btnList[i - 1].text = (date + i - week).toString()
        }
        startDate = Utils.getTimestampFromDate(year.toString() + "-" + month + "-" + btn1.text.toString(), "yyyy-MM-dd")
        endDate = Utils.getTimestampFromDate(year.toString() + "-" + month.toString() + "-" + btn7.text.toString(), "yyyy-MM-dd")

    }

    private fun updateContentView(noteList: MutableMap<Long, NoteModel>) {

        var notes: MutableList<NoteModel> = mutableListOf()
        (1..7)
                .map { (date + it - week).toString() }
                .map { Utils.getTimestampFromDate(year.toString() + "-" + month + "-" + it, "yyyy-MM-dd") }
                .mapTo(notes) { noteList[it] ?: NoteModel() }

        adapter = CardViewAdapter(baseContext, notes!!)
        adapter.setOnPageClickListener(View.OnClickListener {
            if (it.tag == 0L) {
                var intent = Intent(CardActivity@ this, EditNoteActivity::class.java)
                intent.putExtra("edit_date", getCurDate(view_pager.currentItem))
                startActivity(intent)
            } else {
                var showNoteDialog = ShowNoteDialog()
                showNoteDialog.todayDate = getCurDate(view_pager.currentItem)
                showNoteDialog.show(supportFragmentManager, "show_note_dialog")
            }
        })
        view_pager.overScrollMode = ViewPager.OVER_SCROLL_NEVER
        view_pager.setOnPageChangeListener(MyPagerOnPageChangeListener())
        view_pager.adapter = adapter
        btnList[mCurrentPageIndex].performClick()
        view_pager.setCurrentItem(mCurrentPageIndex + 1, false)
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

    inner class MyPagerOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//            if (position < 1 && positionOffset < 0.5) {
//                changeContentWeek(-1)
//            } else if (position > 6 && positionOffset > 0.5) {
//                changeContentWeek(1)
//            }
        }


        override fun onPageSelected(position: Int) {
            //java-switch => kotlin-when
            mCurrentPageIndex = position - 1
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

    private fun changeContentWeek(orientation: Int) {
        if (orientation > 0) {
            //right
        } else {
            //left
            date -= 7
            updateCalendarBarValue()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
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

}

