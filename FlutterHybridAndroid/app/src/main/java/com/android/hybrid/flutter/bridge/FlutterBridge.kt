package com.android.hybrid.flutter.bridge

import android.widget.Toast
import com.android.hybrid.flutter.MyFlutterActivity
import com.android.hybrid.util.ActivityManager
import com.android.hybrid.util.AppGlobals
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 12:54
 * Email: hydznsqk@163.com
 * Des:
 */
class FlutterBridge : MethodChannel.MethodCallHandler, IFlutterBridge<Any?, MethodChannel.Result> {


    private var methodChannels = mutableListOf<MethodChannel>()

    //单例
    companion object {
        @JvmStatic
        var instance: FlutterBridge? = null
            private set

        @JvmStatic
        fun init(flutterEngine: FlutterEngine): FlutterBridge? {
            val methodChannel = MethodChannel(flutterEngine.dartExecutor, "FlutterBridge")
            if (instance == null) {
                FlutterBridge().also {
                    instance = it
                }
            }
            methodChannel.setMethodCallHandler(instance)
            //因多FlutterEngine后每个FlutterEngine需要单独注册一个MethodChannel，所以用集合将所有的MethodChannel保存起来
            instance!!.apply {
                methodChannels.add(methodChannel)
            }
            return instance
        }
    }


    /**
     * 原生调用flutter
     */
    fun fire(method: String, argument: Any?) {
        methodChannels.forEach {
            it.invokeMethod(method, argument)
        }
    }

    /**
     * 原生调用flutter
     */
    fun fire(method: String, argument: Any, callback: MethodChannel.Result?) {
        methodChannels.forEach {
            it.invokeMethod(method, argument, callback)
        }
    }

    /**
     * flutter调用原生
     * 处理来自Dart的方法调用
     */
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "onBack" -> onBack(call.arguments)
            "goToNative" -> goToNative(call.arguments)
            "getHeaderParams" -> getHeaderParams(result)
            else -> result.notImplemented()
        }
    }


    override fun onBack(p: Any?) {
        if (ActivityManager.instance.getTopActivity(true) is MyFlutterActivity) {
            (ActivityManager.instance.getTopActivity(true) as MyFlutterActivity).onBackPressed()
        }
    }

    override fun goToNative(p: Any?) {
        if (p is Map<*, *>) {
            val action = p["action"]
            if (action == "goToDetail") {
                val goodsId = p["goodsId"]

                Toast.makeText(AppGlobals.get(),"商品ID="+goodsId,Toast.LENGTH_LONG).show();
            } else if (action == "goToLogin") {
                Toast.makeText(AppGlobals.get(),"去登录",Toast.LENGTH_LONG).show();
            } else {

            }
        }
    }

    /**
     * flutter那边获取boarding-pass和auth-token
     */
    override fun getHeaderParams(callback: MethodChannel.Result) {
        val map = HashMap<String, String>()
        map["boarding-pass"] = "boarding-pass"
        map["auth-token"] = "auth-token"
        callback.success(map)
    }


}