package com.android.hybrid.flutter.bridge

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 12:50
 * Email: hydznsqk@163.com
 * Des:以下定义的方法都是Flutter调用Android
 */
interface IFlutterBridge<P, Callback> {

    /**
     * 返回到上一页,一般用于Flutter点击返回按钮,然后关闭原生页面
     */
    fun onBack(p: P?)

    /**
     * 去Android页面或者传递数据到Android这边
     */
    fun goToNative(p: P)

    /**
     * 获取到Android这边的Header信息
     */
    fun getHeaderParams(callback: Callback)

}