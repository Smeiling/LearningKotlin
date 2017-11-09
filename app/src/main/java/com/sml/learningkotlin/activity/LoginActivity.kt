package com.sml.learningkotlin.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sml.learningkotlin.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title_bar.view.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTitleBar()
        initContentView()

    }

    private fun initContentView() {
        tv_register.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tv_register.setOnClickListener(View.OnClickListener {
            startActivity(Intent(LoginActivity@ this, RegisterActivity::class.java))
        })
    }

    private fun initTitleBar() {
        title_bar.iv_right.setImageResource(R.mipmap.icon_check)
        title_bar.iv_right.setOnClickListener(View.OnClickListener {
            if ("smeiling".equals(editText.text.toString()) && "smlsmlsml".equals(editText2.text.toString())) {
                startActivity(Intent(MainActivity@ this, CardActivity::class.java))
            }
        })
        title_bar.iv_left.visibility = View.INVISIBLE
    }
}
