package com.android.hybrid.flutter.view

import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

/**
 * 将Native UI 嵌入到Flutter中去使用,需要准备4件套
 * 1.FImageView,要嵌入到Flutter的iOS view 或者 Android view
 * 2.FImageViewController,FImageView的控制器,用来创建和管理FImageView
 * 3.FImageViewFactory,用于向Flutter提供FImageView
 * 4.FImageViewPlugin,用于向Flutter注册FImageView
 *
 * 接下来,我们就以图片为例,把ImageView控件嵌入到Flutter中进行使用
 */
class FImageViewFactory(private val messenger: BinaryMessenger) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        return FImageViewController(context, messenger, viewId, args)
    }
}