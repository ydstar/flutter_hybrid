package com.android.hybrid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.hybrid.old_bridge.FlutterAppActivity
import com.android.hybrid.old_bridge.OldBridgeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 旧版通信
     */
    fun test(view: View) {
        startActivity(Intent(this,OldBridgeActivity::class.java))
    }

    /**
     * 新版通信
     */
    fun test2(view: View) {

    }
}