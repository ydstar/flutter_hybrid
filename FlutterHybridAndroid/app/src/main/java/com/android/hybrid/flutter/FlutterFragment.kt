package com.android.hybrid.flutter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.android.hybrid.R
import com.android.hybrid.fragment.BaseFragment
import com.android.hybrid.util.AppGlobals
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import kotlinx.android.synthetic.main.fragment_flutter.*

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 14:32
 * Email: hydznsqk@163.com
 * Des:
 */
abstract class FlutterFragment(moduleName: String) : BaseFragment() {


    private val flutterEngine: FlutterEngine?
    private lateinit var flutterView: FlutterView

    private val cached =  FlutterCacheManager.instance!!.hastCached(moduleName)

    init {
        flutterEngine =
            FlutterCacheManager.instance!!.getCachedFlutterEngine(AppGlobals.get(), moduleName)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_flutter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 注册flutter/platform_views 插件以便能够处理native view
        if(!cached){
            flutterEngine?.platformViewsController?.attach(activity,flutterEngine.renderer,flutterEngine.dartExecutor)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (mLayoutView as ViewGroup).addView(createFlutterView(activity!!))
    }

    private fun createFlutterView(context: Context): FlutterView {
        val flutterTextureView = FlutterTextureView(activity!!)
        flutterView = FlutterView(context, flutterTextureView)
        return flutterView
    }

    /**
     * 设置标题
     */
    fun setTitle(titleStr: String) {
        rl_title.visibility = View.VISIBLE
        title_line.visibility = View.VISIBLE
        title.text = titleStr
    }

    /**
     * 生命周期告知flutter
     */
    override fun onStart() {
        flutterView.attachToFlutterEngine(flutterEngine!!)
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        //for flutter >= v1.17
        flutterEngine!!.lifecycleChannel.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine!!.lifecycleChannel.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine!!.lifecycleChannel.appIsPaused()
    }

    override fun onDetach() {
        super.onDetach()
        flutterEngine!!.lifecycleChannel.appIsDetached()
    }

    override fun onDestroy() {
        super.onDestroy()
        flutterView.detachFromFlutterEngine()
    }


}