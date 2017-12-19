package com.sml.learningkotlin.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.sml.learningkotlin.R
import com.sml.learningkotlin.activity.EditNoteActivity
import com.sml.learningkotlin.model.NoteModel
import com.sml.learningkotlin.utils.CardType
import com.sml.learningkotlin.utils.Utils
import kotlinx.android.synthetic.main.dialog_show_note.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Smeiling on 2017/12/7.
 */
class ShowNoteDialog : DialogFragment() {

    var todayDate: String = ""
    var curObjId: String = ""
    var cardType: CardType = CardType.THEME_BLUE

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var displayMetrics = DisplayMetrics()
        dialog.window.setLayout((displayMetrics.widthPixels * 0.8).toInt(), (displayMetrics.widthPixels * 0.8).toInt())
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var view = inflater?.inflate(R.layout.dialog_show_note, container, false)
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initTitleBar()
        initBottomBar()
        updateCardType()
        requestData()
    }

    private fun updateCardType() {
        var color = Color.parseColor(cardType.rgb)
        dialog_title.setBackgroundColor(color)
        et_title.setHintTextColor(color)
    }

    private fun requestData() {
        var query = AVQuery<AVObject>("NoteModel")
        query.whereEqualTo("timestamp", Utils.getTimestampFromDate(todayDate, "yyyy-MM-dd"))
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(p0: MutableList<AVObject>?, p1: AVException?) {
                if (p1 == null) {
                    if (p0!!.size > 0) {
                        curObjId = p0[0].objectId ?: ""
                        var noteModel = NoteModel(p0[0].getString("title"),
                                p0[0].getString("content"),
                                p0[0].getString("date"),
                                p0[0].getString("card_type"))
                        updateView(noteModel)
                    } else {
                        updateView(null)
                    }
                } else {
                    updateView(null)
                }
            }
        })
    }

    private fun updateView(note: NoteModel?) {
        et_title.text = note?.title
        et_content.text = note?.content
        cardType = note?.cardType!!
        updateCardType()
    }

    private fun initBottomBar() {
        iv_calendar.visibility = View.GONE
        iv_photo.visibility = View.GONE
    }

    private fun initTitleBar() {
        if (TextUtils.isEmpty(todayDate)) {
            todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        }

        dialog_title_text.text = todayDate
        dialog_iv_right.setImageDrawable(Utils.getTintedDrawable(context, R.mipmap.icon_edit, Color.WHITE))
        dialog_iv_right.setOnClickListener({
            var intent = Intent(activity, EditNoteActivity::class.java)
            intent.putExtra("edit_date", todayDate)
            // 转场动画
            var option = ActivityOptionsCompat.makeScaleUpAnimation(dialog_title,
                    0,
                    0,
                    Utils.getWindowWidth(activity),
                    Utils.dpi2px(activity, 48f))
            startActivity(intent, option.toBundle())
            dismissAllowingStateLoss()
        })
    }

}