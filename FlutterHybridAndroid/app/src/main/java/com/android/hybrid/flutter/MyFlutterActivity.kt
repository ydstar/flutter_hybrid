package com.android.hybrid.flutter

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hybrid.R
import com.android.hybrid.util.IStatusBar


/**
 * Author: 信仰年轻
 * Date: 2021-06-11 14:42
 * Email: hydznsqk@163.com
 * Des:
 */
class MyFlutterActivity : AppCompatActivity() {


    var moduleName: String? = null
    var flutterFragment: MFlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IStatusBar.setStatusBar(this, true, statusBarColor = Color.TRANSPARENT, translucent = true)
        setContentView(R.layout.activity_flutter)
        initFragment()
    }

    private fun initFragment() {
        moduleName=intent.getStringExtra("moduleName")
        flutterFragment = MFlutterFragment(moduleName!!)
        supportFragmentManager.beginTransaction().add(R.id.root_view, flutterFragment!!)
            .commit()
    }

    class MFlutterFragment(private var moduleName:String) : FlutterFragment(moduleName){

        override fun onDestroy() {
            super.onDestroy()
            //销毁Flutter引擎
            FlutterCacheManager.instance?.destroyCached(moduleName)
        }
    }
}