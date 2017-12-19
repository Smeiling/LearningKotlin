package com.sml.learningkotlin.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sml.learningkotlin.R
import com.sml.learningkotlin.utils.CardType
import kotlinx.android.synthetic.main.dialog_color_choose.view.*

/**
 * Created by Smeiling on 2017/12/19.
 */
class ColorChooseDialog : DialogFragment() {

    private lateinit var onColorChooseListener: OnColorChooseListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var displayMetrics = DisplayMetrics()
        //dialog.window.setLayout((displayMetrics.widthPixels * 0.8).toInt(), (displayMetrics.widthPixels * 0.8).toInt())
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater!!.inflate(R.layout.dialog_color_choose, container, false)
        view.ic_blue.setOnClickListener({
            onColorChooseListener?.onColorChoosed(CardType.THEME_BLUE)
        })
        view.ic_green.setOnClickListener({
            onColorChooseListener?.onColorChoosed(CardType.GREEN)
        })
        view.ic_pink.setOnClickListener({
            onColorChooseListener?.onColorChoosed(CardType.PINK)
        })
        return view
    }

    fun setOnColorChooseListener(listener: OnColorChooseListener) {
        this.onColorChooseListener = listener
    }

    /**
     * 颜色选择回调
     */
    interface OnColorChooseListener {
        fun onColorChoosed(color: CardType)
    }

}