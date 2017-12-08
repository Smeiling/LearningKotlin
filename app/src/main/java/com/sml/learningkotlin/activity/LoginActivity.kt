package com.sml.learningkotlin.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
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
            var intent = Intent(LoginActivity@ this, RegisterActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initTitleBar() {
        title_bar.iv_right.setImageResource(R.mipmap.icon_check)
        title_bar.iv_right.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(editText.text.toString()) && !TextUtils.isEmpty(editText2.text.toString())) {
                doLogin()
            }
        })
        title_bar.iv_left.visibility = View.INVISIBLE
    }

    /**
     * 登录请求
     */
    private fun doLogin() {
        AVUser.logInInBackground(editText.text.toString(), editText2.text.toString(), object : LogInCallback<AVUser>() {
            override fun done(p0: AVUser?, p1: AVException?) {
                if (p1 == null && p0 != null) {
                    //saveAccountInfo(p0.sessionToken)
                    Toast.makeText(baseContext, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(baseContext, CardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, p1?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * 保存当前登录token用于自动登录
     */
    private fun saveAccountInfo(token: String) {
        val sp = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("current_token", token)
    }

}
