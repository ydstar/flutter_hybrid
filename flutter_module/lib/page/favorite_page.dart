import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';

class FavoritePage extends StatefulWidget {
  @override
  _FavoritePageState createState() => _FavoritePageState();
}

class _FavoritePageState extends State<FavoritePage> {
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
    bridge.register("onRefreshFavorite", (MethodCall call) {
      setState(() {
        arguments = call.arguments;
      });
      return Future.value("Flutter 收到,我是收藏");
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          children: [
            Text(
              "收藏---${arguments}",
              style: TextStyle(fontSize: 20),
            ),
            MaterialButton(onPressed: (){

              var map = {"action":"goToDetail","goodsId":123456};
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
    FlutterBridge.getInstance().unRegister("onRefreshFavorite");
  }
}
