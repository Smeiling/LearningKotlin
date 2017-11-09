package com.sml.learningkotlin.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationSet
import android.widget.RadioButton
import android.widget.RadioGroup
import com.sml.learningkotlin.R
import com.sml.learningkotlin.adapter.CardViewAdapter
import com.sml.learningkotlin.model.NoteModel
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.common_title_bar.view.*

//java-extend,implements => kotlin-:,
class CardActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    var mCurrentCheckedRadioLeft = 0f
    var tabWidth = 0
    var btnList: MutableList<RadioButton> = mutableListOf()

    companion object {
        val TAG = CardActivity.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        initTitleBar()

        btnList.add(btn1)
        btnList.add(btn2)
        btnList.add(btn3)
        btnList.add(btn4)
        btnList.add(btn5)
        btnList.add(btn6)
        btnList.add(btn7)
        initTabWidth()
        initListener()
        initVariable()
        btn1.isChecked = true
        btn1.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        view_pager.currentItem = 1
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft()
    }

    private fun initTitleBar() {
        title_bar.tv_title.text = "SMLSMLSML"
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

    private fun initVariable() {

        var notes: MutableList<NoteModel> = (1..7)
                .map { NoteModel("smlsmlsml" + it, "ABCDEFGHIJKLMNOPQRSTUVWXYZ ", "2017-11-11") }
                .toMutableList()
//        for (i in 1..7) {
//            var note = NoteModel("smlsmlsml" + i, "2017-11-11")
//            notes.add(note)
//        }
        var adapter = CardViewAdapter(this, notes)
        view_pager.adapter = adapter
    }

    private fun initListener() {
        //java-this => kotlin-ClassName@this
        radioGroup.setOnCheckedChangeListener(CardActivity@ this)
        view_pager.overScrollMode = ViewPager.OVER_SCROLL_NEVER
        view_pager.setOnPageChangeListener(MyPagerOnPageChangeListener())
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        //java-ClassName varName -> kotlin-var varName
        var animationSet = AnimationSet(true)

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

