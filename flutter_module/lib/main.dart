import 'package:flutter/material.dart';
import 'package:flutter_module/page/favorite_page.dart';
import 'package:flutter_module/page/native_page.dart';
import 'package:flutter_module/page/recommend_page.dart';



//至少要有一个入口,而且这下面的man() 和 recommend()函数名字 要和FlutterCacheManager中定义的对应上
void main() => runApp(MyApp(FavoritePage()));

@pragma('vm:entry-point')
void recommend() => runApp(MyApp(RecommendPage()));


@pragma('vm:entry-point')
void nativeView() => runApp(MyApp(NativePage()));

class MyApp extends StatelessWidget {

  final Widget page;

  const MyApp(this.page);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
        body: page,
      ),
    );
  }
}
