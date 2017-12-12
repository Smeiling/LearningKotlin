package com.sml.learningkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.avos.avoscloud.AVUser
import com.sml.learningkotlin.R
import com.sml.learningkotlin.model.SettingsItem
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.common_title_bar.view.*
import kotlinx.android.synthetic.main.item_settings.view.*


/**
 * Created by Smeiling on 2017/12/7.
 */
class SettingsActivity : AppCompatActivity() {

    lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initTitleBar()
        initContentView()
        initData()
    }

    private fun initTitleBar() {
        title_bar.tv_title.text = "Settings"
        title_bar.iv_left.setImageResource(R.mipmap.icon_back)
        title_bar.iv_left.setOnClickListener({
            finish()
        })
        title_bar.iv_right.visibility = View.INVISIBLE
    }

    private fun initContentView() {
        var items = mutableListOf<SettingsItem>()
        var keys = resources.getStringArray(R.array.SettingsItem)
        keys.forEach {
            var item = SettingsItem()
            item.menuKey = it.split(":")[1]
            item.editable = it.split(":")[0] == "0"
            items.add(item)
        }
        adapter = SettingsAdapter(items)
        settings_list.adapter = adapter
        tv_logout.setOnClickListener({
            doLogout()
        })
    }


    private fun initData() {
        
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


    inner class SettingsAdapter(private var items: List<SettingsItem>) : BaseAdapter() {
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var view = LayoutInflater.from(baseContext).inflate(R.layout.item_settings, null)
            view.item_key.text = items[p0].menuKey
            view.item_value.text = items[p0].menuValue
            return view
        }

        override fun getItem(p0: Int): Any {
            return items[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return items.size
        }

    }
}