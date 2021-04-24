package com.android.hybrid.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.android.hybrid.R
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor


abstract class FlutterFragment() : BaseFragment() {
    private lateinit var flutterEngine: FlutterEngine
    protected var flutterView: FlutterView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_flutter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (layoutView as ViewGroup).addView(createFlutterView(activity!!))
    }

    private fun createFlutterView(context: Context): FlutterView {
        //使用FlutterTextureView来进行渲染，以规避FlutterSurfaceView压后台回来后界面被复用的问题
        val flutterTextureView = FlutterTextureView(activity!!)
        flutterView = FlutterView(context, flutterTextureView)
        return flutterView!!
    }

    override fun onStart() {
        flutterView!!.attachToFlutterEngine(flutterEngine!!)
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
        flutterView?.detachFromFlutterEngine()
    }
}