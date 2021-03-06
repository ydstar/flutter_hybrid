import 'package:flutter/services.dart';

class FImageViewController{

  MethodChannel _channel;

  FImageViewController(int id){
    _channel = MethodChannel("FImageView_$id");
    _channel.setMethodCallHandler(_handleMethod);
  }


  ///来自native view的调用
  Future<dynamic> _handleMethod(MethodCall call) async{
    switch(call.method){
      case "your method name":
        var text = call.arguments as String;
        return new Future.value("Text from native:$text");
    }
  }


  ///调用native view
  Future<void> setUrl(String url) async{
    try{
      String result = await _channel.invokeMethod("setUrl",{"url":url});
      print("Result from native:$result");
    } on PlatformException catch(e){
      print("Error from native:${e.message}");
    }
  }
}