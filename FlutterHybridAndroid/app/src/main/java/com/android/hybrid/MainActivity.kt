package com.android.hybrid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.hybrid.flutter.FlutterAppActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test(view: View) {

        val beginTransaction = supportFragmentManager.beginTransaction()
        val fragment = TestFragment()

        beginTransaction.replace(R.id.fl_container,fragment)
        beginTransaction.commit()



    }

    fun test2(view: View) {

        FlutterAppActivity.start(this@MainActivity,"哈哈哈哈")
    }
}