import 'package:flutter/material.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';
import 'package:flutter_module/native/f_image_view.dart';
import 'package:flutter_module/native/f_image_view_controller.dart';


class NativePage extends StatefulWidget {
  @override
  _NativePageState createState() => _NativePageState();
}

class _NativePageState extends State<NativePage> {
  String url2 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ff3a58b7a2e90267fcbe72963c5a0b1e305b513ec17fcf-PTscFs_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1626323626&t=c5281de39d048b77ebb9442bc0bac382";
  String url1 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Fsmccloud%2Frecom%2F2015%2F06%2F12%2F143410423483487541.JPEG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1626323668&t=74a0b9eb57a4dbeeec16b599d5a97ed3";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: BackButton(
          onPressed: () {
            FlutterBridge.getInstance().onBack(null);
          },
        ),
        title: Text("Native view"),
      ),
      body: Column(
        children: [
          Text("Flutter嵌入Native组件"),
          SizedBox(
              height: 520,
              child: FImageView(
                url:url1,
                onViewCreated: _onViewCreated,
              ))
        ],
      ),
    );
  }

  void _onViewCreated(FImageViewController controller) {
    Future.delayed(Duration(milliseconds: 3000), () {
      controller
          .setUrl(url2);
    });
  }
}
