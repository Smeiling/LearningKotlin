package com.sml.learningkotlin

import android.app.Application
import com.avos.avoscloud.AVAnalytics
import com.avos.avoscloud.AVOSCloud

/**
 * Created by Smeiling on 2017/12/4.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AVOSCloud.initialize(this,"9IuRFVKsRl2Y1My8zL9LVr8S-gzGzoHsz", "6Yp20RHrHYUC9fuG9J7XRNXe")
        AVOSCloud.setDebugLogEnabled(true)
        AVAnalytics.enableCrashReport(this, true);
    }
}