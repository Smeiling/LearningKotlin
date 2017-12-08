package com.sml.learningkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avos.avoscloud.AVUser
import com.sml.learningkotlin.R
import kotlinx.android.synthetic.main.activity_settings.*


/**
 * Created by Smeiling on 2017/12/7.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        tv_logout.setOnClickListener({
            doLogout()
        })
    }

    /**
     * 退出当前账户并返回登录页面
     */
    private fun doLogout() {
        AVUser.logOut()// 清除缓存用户对象
        if (AVUser.getCurrentUser() == null) {
            startActivity(Intent(SettingsActivity@ this, LoginActivity::class.java))
            finish()
        }
    }
}