import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';


class RecommendPage extends StatefulWidget {
  @override
  _RecommendPageState createState() => _RecommendPageState();
}

class _RecommendPageState extends State<RecommendPage> {
  @override
  bool get wantKeepAlive => true; //保活,切换tab的时候不会重新刷新页面


  @override
  void initState() {
    _registerEvent();
    super.initState();
  }
  var arguments;

  ///注册事件,登录成功后会发射事件到这里
  void _registerEvent() {
    var bridge = FlutterBridge.getInstance();
    //监听onRefresh消息,登录的时候和点击当前页面标题的时候会发射到这里,然后请求数据进行刷新
    bridge.register("onRefreshRecommend", (MethodCall call) {
      setState(() {
        arguments = call.arguments;
      });
      return Future.value("Flutter 收到,我是推荐");
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          children: [
            Text(
              "推荐---${arguments}",
              style: TextStyle(fontSize: 20),
            ),
            MaterialButton(onPressed: (){
              var map = {"action":"goToLogin"};
              FlutterBridge.getInstance().goToNative(map);
            },child: Text("Flutter 调用 Android"),)
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    FlutterBridge.getInstance().unRegister("onRefreshRecommend");
  }
}
