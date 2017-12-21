package com.sml.learningkotlin.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import com.avos.avoscloud.*
import com.ldf.calendar.interf.OnSelectDateListener
import com.ldf.calendar.model.CalendarDate
import com.sml.learningkotlin.R
import com.sml.learningkotlin.dialog.CalendarDialog
import com.sml.learningkotlin.dialog.ColorChooseDialog
import com.sml.learningkotlin.model.CardModel
import com.sml.learningkotlin.utils.CardType
import com.sml.learningkotlin.utils.Utils
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.android.synthetic.main.common_title_bar.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Smeiling on 2017/11/15.
 */
class EditCardActivity : AppCompatActivity(), ColorChooseDialog.OnColorChooseListener {


    var todayDate: String = ""
    var curObjId: String = ""
    var cardType: CardType = CardType.THEME_BLUE
    lateinit var dialog: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        initTitleBar()
        initBottomBar()
        updateCardType()
        requestData()
    }


    private fun requestData() {
        var query = AVQuery<AVObject>("CardModel")
        query.whereEqualTo("timestamp", Utils.getTimestampFromDate(todayDate, "yyyy-MM-dd"))
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(p0: MutableList<AVObject>?, p1: AVException?) {
                if (p1 == null) {
                    if (p0!!.size > 0) {
                        curObjId = p0[0].objectId ?: ""
                        var cardModel = CardModel(p0[0].getString("title"), p0[0].getString("content"), p0[0].getString("date"))
                        updateView(cardModel)
                    } else {
                        updateView(null)
                    }
                } else {
                    updateView(null)
                }
            }
        })
    }

    private fun updateView(card: CardModel?) {
        et_title.setText(card?.title)
        et_content.setText(card?.content)
    }

    private fun initBottomBar() {
        iv_calendar.setOnClickListener({
            var dialog = CalendarDialog()
            dialog.selectDateListener = object : OnSelectDateListener {
                override fun onSelectDate(p0: CalendarDate?) {
                    todayDate = p0.toString()
                    title_bar.tv_title.text = todayDate
                    requestData()
                    dialog.dismissAllowingStateLoss()
                }

                override fun onSelectOtherMonth(p0: Int) {

                }
            }
            dialog.show(supportFragmentManager, "calendar_dialog")
        })

        iv_weather.setOnClickListener({
            //            var dialog = WeatherDialog()
//            dialog.show(supportFragmentManager, "weather_dialog")
            //showWeatherDialog()
        })

        iv_color.setOnClickListener({
            dialog = ColorChooseDialog()
            if (dialog is ColorChooseDialog) {
                (dialog as ColorChooseDialog).setOnColorChooseListener(this)
            }
            dialog.show(supportFragmentManager, "color_choose_dialog")
        })
    }

    private fun initTitleBar() {
        todayDate = intent.getStringExtra("edit_date") ?: ""
        if (TextUtils.isEmpty(todayDate)) {
            todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        }
        title_bar.tv_title.text = todayDate
        title_bar.tv_title.setTextColor(Color.WHITE)
        title_bar.iv_left.setImageDrawable(Utils.getTintedDrawable(this, R.mipmap.icon_back, Color.WHITE))
        title_bar.iv_left.setOnClickListener({
            finish()
        })

        title_bar.iv_right.setImageDrawable(Utils.getTintedDrawable(this, R.mipmap.icon_check, Color.WHITE))
        title_bar.iv_right.setOnClickListener({
            if (!TextUtils.isEmpty(et_title.text) && !TextUtils.isEmpty(et_content.text)) {
                var card = CardModel(et_title.text.toString(), et_content.text.toString(), todayDate.replace("-", ""))
                saveCard(card)
            }
        })
    }

    private fun saveCard(cardModel: CardModel) {
        if (!TextUtils.isEmpty(curObjId)) {
            var updateCard = AVObject.createWithoutData("CardModel", curObjId)
            updateCard.put("title", cardModel.title)
            updateCard.put("content", cardModel.content)
            updateCard.put("date", cardModel.date)
            updateCard.put("timestamp", cardModel.timestamp)
            updateCard.put("card_type", cardType)
            updateCard.saveInBackground(object : SaveCallback() {
                override fun done(p0: AVException?) {
                    if (p0 == null) {
                        Toast.makeText(baseContext, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(baseContext, p0.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            var card = AVObject("CardModel")
            card.put("title", cardModel.title)
            card.put("content", cardModel.content)
            card.put("date", cardModel.date)
            card.put("timestamp", cardModel.timestamp)
            card.put("card_type", cardType)
            card.saveInBackground(object : SaveCallback() {
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

    public fun showWeatherDialog() {
        var contentView = LayoutInflater.from(baseContext).inflate(R.layout.dialog_weather, null)
        var popupWindow = PopupWindow(baseContext)
        popupWindow.contentView = contentView
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAsDropDown(iv_weather, 0, -Utils.dpi2px(baseContext, 220f))
    }

    override fun onColorChoosed(color: CardType) {
        cardType = color
        dialog.dismissAllowingStateLoss()
        updateCardType()
    }

    private fun updateCardType() {
        val color = Color.parseColor(cardType.rgb)
        title_bar.title_container.setBackgroundColor(color)
        divider_line.setBackgroundColor(color)
    }
}