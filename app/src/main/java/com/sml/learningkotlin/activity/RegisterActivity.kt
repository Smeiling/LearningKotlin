package com.sml.learningkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SignUpCallback
import com.sml.learningkotlin.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.common_title_bar.view.*

/**
 * Created by Smeiling on 2017/11/9.
 */
class RegisterActivity : AppCompatActivity(), TextWatcher {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initTitle()
        initView()
    }

    private fun initView() {
        et_username.addTextChangedListener(this)
        et_password.addTextChangedListener(this)
        et_repassword.addTextChangedListener(this)
        tv_login.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun initTitle() {
        title_bar.tv_title.text = "REGISTER"
        title_bar.iv_left.setImageResource(R.mipmap.icon_back)
        title_bar.iv_right.setImageResource(R.mipmap.icon_check)

        title_bar.iv_left.setOnClickListener(View.OnClickListener {
            finish()
        })

        title_bar.iv_right.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(et_username.text) && !TextUtils.isEmpty(et_password.text)) {
                if (et_password.text != et_repassword.text) {
                    doRegister()
                } else {
                    iv_repassword.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun doRegister() {
        var user = AVUser()
        user.username = et_username.text.toString()
        user.setPassword(et_password.text.toString())
        user.signUpInBackground(object : SignUpCallback() {
            override fun done(p0: AVException?) {
                if (p0 == null) {
                    startActivity(Intent(applicationContext, CardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, p0?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun afterTextChanged(p0: Editable?) {
//        var userName = et_username.text
//        //用正则式匹配文本获取匹配器
//        val matcher = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{7,11}").matcher(userName)
//        if (!userName.isEmpty() && !matcher.matches()) {
//            iv_username.visibility = View.VISIBLE
//        } else {
//            iv_username.visibility = View.GONE
//        }
//
//        var password = et_password.text
//        val pwdMatcher = Pattern.compile("^[a-zA-Z]\\w{5,17}\$").matcher(password)
//        if (!password.isEmpty() && !pwdMatcher.matches()) {
//            iv_password.visibility = View.VISIBLE
//        } else {
//            iv_password.visibility = View.GONE
//        }
//        if (!et_password.text.isEmpty() && password == et_repassword.text) {
//            iv_repassword.visibility = View.VISIBLE
//        } else {
//            iv_repassword.visibility = View.GONE
//        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}