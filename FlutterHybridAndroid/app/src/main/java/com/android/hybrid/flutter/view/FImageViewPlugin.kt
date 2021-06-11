package com.android.hybrid.flutter.view

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry
import io.flutter.plugin.common.PluginRegistry

/**
 * 将Native UI 嵌入到Flutter中去使用,需要准备4件套
 * 1.FImageView,要嵌入到Flutter的iOS view 或者 Android view
 * 2.FImageViewController,FImageView的控制器,用来创建和管理FImageView
 * 3.FImageViewFactory,用于向Flutter提供FImageView
 * 4.FImageViewPlugin,用于向Flutter注册FImageView
 *
 * 接下来,我们就以图片为例,把ImageView控件嵌入到Flutter中进行使用
 */
object FImageViewPlugin {


    /**
     * 需要在创建Flutter引擎的时候去注册(重要)
     */
    fun registerWith(flutterEngine: FlutterEngine){
        val shimPluginRegistry = ShimPluginRegistry(flutterEngine)
        registerWith(shimPluginRegistry.registrarFor("Flutter"))
    }

    fun registerWith(register:PluginRegistry.Registrar){
        val fImageViewFactory = FImageViewFactory(register.messenger())
        register.platformViewRegistry().registerViewFactory("FImageView",fImageViewFactory)
    }




}