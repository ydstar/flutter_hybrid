import 'package:flutter/material.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';
import 'package:flutter_module/native/f_image_view.dart';
import 'package:flutter_module/native/f_image_view_controller.dart';


class NativePage extends StatefulWidget {
  @override
  _NativePageState createState() => _NativePageState();
}

class _NativePageState extends State<NativePage> {
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
              height: 300,
              child: FImageView(
                url:
                    "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
                onViewCreated: _onViewCreated,
              ))
        ],
      ),
    );
  }

  void _onViewCreated(FImageViewController controller) {
    Future.delayed(Duration(milliseconds: 8000), () {
      controller
          .setUrl("https://www.devio.org/img/beauty_camera/beauty_camera2.jpg");
    });
  }
}
