package com.android.hybrid.flutter.view

import android.content.Context
import android.view.View
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

/**
 * 将Native UI 嵌入到Flutter中去使用,需要准备4件套
 * 1.FImageView,要嵌入到Flutter的iOS view 或者 Android view
 * 2.FImageViewController,FImageView的控制器,用来创建和管理FImageView
 * 3.FImageViewFactory,用于向Flutter提供FImageView
 * 4.FImageViewPlugin,用于向Flutter注册FImageView
 *
 * 接下来,我们就以图片为例,把ImageView控件嵌入到Flutter中进行使用
 */
class FImageViewController(context: Context, messenger: BinaryMessenger, id: Int?, args: Any?) :
    PlatformView, MethodChannel.MethodCallHandler {

    private val imageView: FImageView = FImageView(context)
    private var methodChannel: MethodChannel

    init {
        //通信
        methodChannel = MethodChannel(messenger, "FImageView_$id")
        methodChannel.setMethodCallHandler(this)
        if (args is Map<*, *>) {
            imageView.setUrl(args["url"] as String)
        }
        print(args)
    }

    /**
     * 用于返回Native View,这个View会被嵌入到Flutter的视图结构中
     */
    override fun getView(): View {
        return imageView
    }

    /**
     * 当Flutter要决定销毁PlatformView时会调用这个方法,
     * 通常在Flutter的一个widget销毁时调用,我们可以在这个方法中做一些资源释放的工作
     */
    override fun dispose() {
    }

    //通信flutter调用原生
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "setUrl" -> {
                val url = call.argument<String>("url")
                if (url != null) {
                    imageView.setUrl(url)
                    result.success("setUrl  success")
                } else {
                    result.error("-1", "url cannot be null", null)
                }
            }
            else -> {
                result.notImplemented()
            }
        }
    }
}