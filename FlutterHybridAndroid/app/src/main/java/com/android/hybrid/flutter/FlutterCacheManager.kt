package com.android.hybrid.flutter

import android.content.Context
import android.os.Looper
import com.android.hybrid.flutter.bridge.FlutterBridge
import com.android.hybrid.flutter.view.FImageViewPlugin
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterJNI
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 13:50
 * Email: hydznsqk@163.com
 * Des:
 * Flutter优化提升加载速度，实现秒开Flutter模块
 * 让预加载不损失"首页"性能
 * 实例化多个Flutter引擎并分别加载不同的dart 入口文件
 */
class FlutterCacheManager private constructor() {

    /**
     * 伴生对象,保持单例
     */
    companion object {

        //喜欢页面,默认是flutter启动的主入口
        const val MODULE_NAME_FAVORITE = "main"
        //推荐页面
        const val MODULE_NAME_RECOMMEND = "recommend"

        @JvmStatic
        @get:Synchronized
        var instance: FlutterCacheManager? = null
            get() {
                if (field == null) {
                    field = FlutterCacheManager()
                }
                return field
            }
            private set
    }


    /**
     * 空闲时候预加载Flutter
     */
    fun preLoad(context: Context){
        //在线程空闲时执行预加载任务
        Looper.myQueue().addIdleHandler {
            initFlutterEngine(context, MODULE_NAME_FAVORITE)
            initFlutterEngine(context, MODULE_NAME_RECOMMEND)
            false
        }
    }

    fun hastCached(moduleName: String):Boolean{
        return FlutterEngineCache.getInstance().contains(moduleName)
    }

    /**
     * 初始化Flutter
     */
    private fun initFlutterEngine(context: Context, moduleName: String): FlutterEngine {

        val flutterLoader: FlutterLoader = FlutterInjector.instance().flutterLoader()

        //flutter 引擎
        val flutterEngine = FlutterEngine(context,flutterLoader, FlutterJNI())
        //插件注册要紧跟引擎初始化之后，否则会有在dart中调用插件因为还未初始化完成而导致的时序问题
        FlutterBridge.init(flutterEngine)
        FImageViewPlugin.registerWith(flutterEngine)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(
                flutterLoader.findAppBundlePath(),
                moduleName
            )
        )
        //存到引擎缓存中
        FlutterEngineCache.getInstance().put(moduleName,flutterEngine)
        return flutterEngine
    }

    /**
     * 获取缓存的flutterEngine
     */
    fun getCachedFlutterEngine(context: Context?, moduleName: String):FlutterEngine{
        var flutterEngine = FlutterEngineCache.getInstance()[moduleName]
        if(flutterEngine==null && context!=null){
            flutterEngine=initFlutterEngine(context,moduleName)
        }
        return flutterEngine!!
    }

    /**
     * 销毁FlutterEngine
     */
    fun destroyCached(moduleName: String){
        val map = FlutterEngineCache.getInstance()
        if(map.contains(moduleName)){
            map[moduleName]?.apply {
                destroy()
            }
            map.remove(moduleName)
        }
    }
}