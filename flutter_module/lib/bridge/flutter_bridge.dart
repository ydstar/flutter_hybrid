import 'package:flutter/services.dart';

class FlutterBridge {
  static FlutterBridge _instance = FlutterBridge._();

  //该名称还要Android侧的保持一致
  MethodChannel _bridge = const MethodChannel("FlutterBridge");

  var _listenerMap = {}; //map key:String  value:MethodCall方法

  var header;

  FlutterBridge._() {
    _bridge.setMethodCallHandler((MethodCall call) {
      String method = call.method;
      if (_listenerMap[method] != null) {
        return _listenerMap[method](call);
      }
      return null;
    });
  }

  static FlutterBridge getInstance() {
    return _instance;
  }

///////以下方法为Android调用Flutter///////////////////////////////////////////////
  ///注册
  void register(String method, Function(MethodCall) callBack) {
    _listenerMap[method] = callBack;
  }

  ///解除注册
  void unRegister(String method) {
    if (_listenerMap.containsKey(method)) {
      _listenerMap.remove(method);
    }
  }

///////以下方法为Flutter调用Android/////////////////////////////////////////////////
  ///去Android页面或者传递数据到Android这边
  void goToNative(Map params) {
    _bridge.invokeMethod("goToNative", params);
  }

  ///返回到上一页,一般用于Flutter点击返回按钮,然后关闭原生页面
  void onBack(Map params) {
    _bridge.invokeMethod("onBack", params);
  }

  ///获取到Android这边的Header信息
  Future<Map<String, String>> getHeaderParams() async {
    Map header = await _bridge.invokeMethod("getHeaderParams", {});
    return this.header = Map<String, String>.from(header);
  }

  MethodChannel bridge() {
    return _bridge;
  }
}
