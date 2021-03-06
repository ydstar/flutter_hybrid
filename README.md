Android和Flutter通信

## 效果预览
<img src="https://github.com/ydstar/flutter_hybrid/blob/main/FlutterHybridAndroid/preview/show.gif" alt="动图演示效果" width="250px">

### 目录
- [项目结构介绍](#项目结构介绍)
- [如何运行](#如何运行)

### 项目结构介绍

```
flutter_hybrid
│   README.md
│
└───FlutterHybridAndroid Android主项目
│
└───flutter_module flutter模块
```

### 如何运行

1. 将整个项目下载下来
2. 用[Android Studio 4.1](https://developer.android.com/studio/preview)及以上版本打开FlutterHybridAndroid项目运行


### Android 主动调用 Flutter
Android侧
``` kotlin
    FlutterBridge.instance!!.fire(
         "onRefreshFavorite",
         "我是收藏的参数",
         object : MethodChannel.Result {
         override fun notImplemented() {
               Toast.makeText(context, "dart那边未实现", Toast.LENGTH_LONG).show()
         }

         override fun error(errorCode: String?,errorMessage: String?,errorDetails: Any?) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
         }

         override fun success(result: Any?) {
                if (result != null) {
                     Toast.makeText(context, result as String, Toast.LENGTH_LONG).show()
                }
             }
         })
```
Flutter侧

``` dart
    var bridge = FlutterBridge.getInstance();
    //监听onRefreshFavorite消息
    bridge.register("onRefreshFavorite", (MethodCall call) {
      print(call.arguments)
      //返回给Android侧
      return Future.value("Flutter 收到,我是收藏");
    });

```
### Flutter 主动调用 Android
Flutter侧
``` dart
   var map = {"action":"goToDetail","goodsId":123456};
   FlutterBridge.getInstance().goToNative(map);
```

Android侧

``` kotlin
    override fun goToNative(p: Any?) {
        if (p is Map<*, *>) {
            val action = p["action"]
            if (action == "goToDetail") {
                val goodsId = p["goodsId"]
                Toast.makeText(AppGlobals.get(),"商品ID="+goodsId,Toast.LENGTH_LONG).show();
            }else {

            }
        }
    }
```

### 具体的详细使用还请参考Demo

## License
```text
Copyright [2021] [ydStar]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
