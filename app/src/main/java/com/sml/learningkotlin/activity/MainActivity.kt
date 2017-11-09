package com.sml.learningkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sml.learningkotlin.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title_bar.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title_bar.iv_right.setOnClickListener(View.OnClickListener {
            if ("smeiling".equals(editText.text.toString()) && "smlsmlsml".equals(editText2.text.toString())) {
                startActivity(Intent(MainActivity@ this, CardActivity::class.java))
            }
        })
    }
}
