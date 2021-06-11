package com.android.hybrid.old_bridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.hybrid.R

/**
 * 旧版本通信(因为太基础了,所以称之为旧版本)
 */
class OldBridgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_bridge)
    }

    fun test(view: View) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val fragment = TestFragment()
        beginTransaction.replace(R.id.fl_container,fragment)
        beginTransaction.commit()
    }

    fun test2(view: View) {
        FlutterAppActivity.start(this@OldBridgeActivity,"哈哈哈哈")
    }
}