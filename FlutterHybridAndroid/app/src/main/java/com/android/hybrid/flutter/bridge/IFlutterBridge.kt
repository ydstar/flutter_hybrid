package com.android.hybrid.flutter.bridge

/**
 * Author: 信仰年轻
 * Date: 2021-06-11 12:50
 * Email: hydznsqk@163.com
 * Des:
 */
interface IFlutterBridge<P, Callback> {

    fun onBack(p: P?)
    fun goToNative(p: P)
    fun getHeaderParams(callback: Callback)

}