package com.android.hybrid.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.hybrid.R
import com.android.hybrid.flutter.FlutterCacheManager
import com.android.hybrid.flutter.FlutterFragment
import com.android.hybrid.flutter.bridge.FlutterBridge
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.fragment_flutter.*

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 15:20
 * Email: hydznsqk@163.com
 * Des: 推荐页面
 */
class RecommendFragment : FlutterFragment(FlutterCacheManager.MODULE_NAME_RECOMMEND) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.title_recommend))


        title.setOnClickListener {

            FlutterBridge.instance!!.fire(
                "onRefreshRecommend",
                "我是推荐的参数",
                object : MethodChannel.Result {
                    override fun notImplemented() {
                        Toast.makeText(context, "dart那边未实现", Toast.LENGTH_LONG).show()
                    }

                    override fun error(
                        errorCode: String?,
                        errorMessage: String?,
                        errorDetails: Any?
                    ) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }

                    override fun success(result: Any?) {
                        if (result != null) {
                            Toast.makeText(context, result as String, Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

}