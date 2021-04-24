import 'dart:async';
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp(window.defaultRouteName));

class MyApp extends StatelessWidget {
  String initParams;

  MyApp(this.initParams);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter 混合开发',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(
        title: 'Flutter 混合开发',
        initParams: initParams,
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  final String title;
  String initParams;

  MyHomePage({Key key, this.title, this.initParams}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  static const EventChannel _eventChannel = EventChannel("EventChannelPlugin");
  static const MethodChannel _methodChannelPlugin =  MethodChannel("MethodChannelPlugin");
  static const BasicMessageChannel<String> _basicMessageChannel = const BasicMessageChannel("BasicMessageChannelPlugin",StringCodec());

  String showMessage = "";

  @override
  void initState(){
    initEventChannelMethod();
    initBasicMessageChannel();
    super.initState();
  }


////eventChannel start////////////////////////////////////////////////////

  StreamSubscription _streamSubscription;
  /**
   * eventChannel 渠道
   */
  void initEventChannelMethod(){
    _streamSubscription=_eventChannel
        .receiveBroadcastStream("123")
        .listen(_onToDart,onError:_onToDartError);
  }

  /**
   * 接受到从native端传送过来的数据
   */
  void _onToDart(message){
    setState(() {
      showMessage = "EventChannel: "+message;
    });
  }
  void _onToDartError(error) {
    print(error);
  }

  @override
  void dispose() {
    if (_streamSubscription != null) {
      _streamSubscription.cancel();
      _streamSubscription = null;
    }
    super.dispose();
  }

////eventChannel end////////////////////////////////////////////////////


////MethodChannel&BasicMessageChannel  start///////////////////////////////

  /**
   * 使用BasicMessageChannel接受来自Native的消息，并向Native回复
   */
  void initBasicMessageChannel(){
    _basicMessageChannel
        .setMessageHandler((String message) => Future<String>(() {
      setState(() {
        showMessage = 'BasicMessageChannel:' + message;
      });
      return "收到Native的消息：" + message;
    }));
  }

  bool _isMethodChannelPlugin = false;
  void _onTextChange(value) async {
    String response;
    try {
      if (_isMethodChannelPlugin) {
        //使用MethodChannel向Native发送消息，并接受Native的回复
        response = await _methodChannelPlugin.invokeMethod('send', value);
      } else {
        //使用BasicMessageChannel向Native发送消息，并接受Native的回复
        response = await _basicMessageChannel.send(value);
      }
    } on PlatformException catch (e) {
      print(e);
    }
    setState(() {
      showMessage = response ?? "";
    });
  }
  void _onChanelChanged(bool value) =>
      setState(() => _isMethodChannelPlugin = value);
////MethodChannel&BasicMessageChannel  end///////////////////////////////


  //样式
  @override
  Widget build(BuildContext context) {
    TextStyle textStyle = TextStyle(fontSize: 20);
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: Container(
          alignment: Alignment.topLeft,
          decoration: BoxDecoration(color: Colors.lightBlueAccent),
          margin: EdgeInsets.only(top: 70),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              SwitchListTile(
                value: _isMethodChannelPlugin,
                onChanged: _onChanelChanged,
                title: Text(_isMethodChannelPlugin
                    ? "MethodChannelPlugin"
                    : "BasicMessageChannelPlugin"),
              ),
              TextField(
                onChanged: _onTextChange,
                decoration: InputDecoration(
                    focusedBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white)),
                    enabledBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white))),
              ),
              Text(
                '收到初始参数initParams:${widget.initParams}',
                style: textStyle,
              ),
              Text(
                'Native传来的数据：' + showMessage,
                style: textStyle,
              )
            ],
          ),
        ));
  }

}
