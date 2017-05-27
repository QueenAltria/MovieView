package com.jp.movieview.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jp.movieview.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initToolBar()

    }

    fun initToolBar(){
        toolbar.title="设置"
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
       // toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
    }
}
