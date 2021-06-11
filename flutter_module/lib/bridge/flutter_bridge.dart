import 'package:flutter/services.dart';

class FlutterBridge {
  static FlutterBridge _instance = FlutterBridge._();

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

  void register(String method, Function(MethodCall) callBack) {
    _listenerMap[method] = callBack;
  }

  void unRegister(String method) {
    if (_listenerMap.containsKey(method)) {
      _listenerMap.remove(method);
    }
  }

  ///去原生页面
  void goToNative(Map params) {
    _bridge.invokeMethod("goToNative", params);
  }


  void onBack(Map params) {
    _bridge.invokeMethod("onBack", params);
  }

  ///获取header的map
  Future<Map<String, String>> getHeaderParams() async {
    Map header = await _bridge.invokeMethod("getHeaderParams", {});
    return this.header = Map<String, String>.from(header);
  }

  MethodChannel bridge() {
    return _bridge;
  }
}
