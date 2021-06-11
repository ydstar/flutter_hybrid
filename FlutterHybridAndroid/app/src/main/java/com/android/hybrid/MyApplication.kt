package com.android.hybrid

import android.app.Application
import com.android.hybrid.flutter.FlutterCacheManager
import com.android.hybrid.util.ActivityManager


/**
 * Author: 信仰年轻
 * Date: 2021-06-11 10:54
 * Email: hydznsqk@163.com
 * Des:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ActivityManager.instance.init(this)
        FlutterCacheManager.instance?.preLoad(this)
    }
}