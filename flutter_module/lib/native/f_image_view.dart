import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/native/f_image_view_controller.dart';

typedef void FImageViewCreatedCallBack(FImageViewController controller);

class FImageView extends StatefulWidget {
  final String url;
  final FImageViewCreatedCallBack onViewCreated;

  const FImageView({Key key, this.url, this.onViewCreated}) : super(key: key);

  @override
  _FImageViewState createState() => _FImageViewState();
}

class _FImageViewState extends State<FImageView> {
  static const StandardMessageCodec _codec = StandardMessageCodec();

  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: "FImageView",
      creationParams: {"url": widget.url},
      creationParamsCodec: _codec,
      onPlatformViewCreated:_onPlatformViewCreated ,
    );
  }


  void _onPlatformViewCreated(int id){
    if(widget.onViewCreated ==null){
      return;
    }
    widget.onViewCreated(FImageViewController(id));
  }
}
