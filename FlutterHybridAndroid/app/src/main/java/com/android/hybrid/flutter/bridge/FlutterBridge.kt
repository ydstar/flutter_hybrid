package com.android.hybrid.flutter.bridge

import android.content.Intent
import android.widget.Toast
import com.android.hybrid.GoodsDetailActivity
import com.android.hybrid.flutter.MyFlutterActivity
import com.android.hybrid.util.ActivityManager
import com.android.hybrid.util.AppGlobals
import com.android.hybrid.util.AppGlobals.get
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 12:54
 * Email: hydznsqk@163.com
 * Des: Flutter通信桥梁,实现了MethodChannel.MethodCallHandler和IFlutterBridge接口,
 * 然后在override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) 方法中接受Flutter那边的调用,
 * 我们要做的就是Android侧和Flutter侧两边的方法名字保存一致
 * onBack(p: Any?)用于返回到上一页
 * goToNative(p: Any?)中的参数是个map,然后我们可以定义几个key,首先是action,这个key表示要做的动作,举个例子,action为goToDetail表示去详情页,
 * action为goToLogin表示去登录页面,action定义好之后在定义具体要传递的值,比如说定义goodId这个key(用来接收从Flutter侧传过来的参数)
 * getHeaderParams(callback: MethodChannel.Result)是获取Android侧这边的头信息,因为Flutter那边也要进行网络请求
 */
class FlutterBridge : MethodChannel.MethodCallHandler, IFlutterBridge<Any?, MethodChannel.Result> {

    //因多FlutterEngine后每个FlutterEngine需要单独注册一个MethodChannel，所以用集合将所有的MethodChannel保存起来
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
            //因为支持多Flutter引擎,多FlutterEngine后每个FlutterEngine需要单独注册一个MethodChannel，所以用集合将所有的MethodChannel保存起来
            instance!!.apply {
                methodChannels.add(methodChannel)
            }
            return instance
        }
    }

///////以下方法为Android调用Flutter/////////////////////////////////////////////////
    /**
     * Android调用flutter
     */
    fun fire(method: String, argument: Any?) {
        methodChannels.forEach {
            it.invokeMethod(method, argument)
        }
    }

    /**
     * Android调用flutter
     */
    fun fire(method: String, argument: Any, callback: MethodChannel.Result?) {
        methodChannels.forEach {
            it.invokeMethod(method, argument, callback)
        }
    }

///////以下方法为Flutter调用Android/////////////////////////////////////////////////
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

    /**
     * 返回到上一页,一般用于Flutter点击返回按钮,然后关闭原生页面
     */
    override fun onBack(p: Any?) {
        if (ActivityManager.instance.getTopActivity(true) is MyFlutterActivity) {
            (ActivityManager.instance.getTopActivity(true) as MyFlutterActivity).onBackPressed()
        }
    }

    /**
     * 去Android页面或者传递数据到Android这边
     */
    override fun goToNative(p: Any?) {
        if (p is Map<*, *>) {
            val action = p["action"]
            if (action == "goToDetail") {
                val goodsId = p["goodsId"]

                //此处也可以使用ARouter路由来进行跳转
                Toast.makeText(AppGlobals.get(),"商品ID="+goodsId,Toast.LENGTH_LONG).show();
                val intent = Intent(get(), GoodsDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                AppGlobals.get()?.startActivity(intent)
            } else if (action == "goToLogin") {
                Toast.makeText(AppGlobals.get(),"去登录",Toast.LENGTH_LONG).show();
            } else {

            }
        }
    }

    /**
     * 获取到Android这边的Header信息
     */
    override fun getHeaderParams(callback: MethodChannel.Result) {
        val map = HashMap<String, String>()
        map["boarding-pass"] = "boarding-pass"
        map["auth-token"] = "auth-token"
        callback.success(map)
    }


}