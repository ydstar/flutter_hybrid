package com.android.hybrid.old_bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.hybrid.old_bridge.channel.BasicMessageChannelPlugin;
import com.android.hybrid.old_bridge.channel.EventChannelPlugin;
import com.android.hybrid.old_bridge.channel.IShowMessage;
import com.android.hybrid.old_bridge.channel.MethodChannelPlugin;

import io.flutter.embedding.android.FlutterActivity;

public class FlutterAppActivity extends FlutterActivity implements IShowMessage {
    private static final String TAG = FlutterAppActivity.class.getSimpleName();

    public final static String INIT_PARAMS = "initParams";
    private UIPresenter uiPresenter;
    private BasicMessageChannelPlugin basicMessageChannelPlugin;
    private EventChannelPlugin eventChannelPlugin;
    private String initParams;

    public static void start(Context context, String initParams) {
        Intent intent = new Intent(context, FlutterAppActivity.class);
        intent.putExtra(INIT_PARAMS, initParams);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams = getIntent().getStringExtra(INIT_PARAMS);
        if (getFlutterEngine() != null) {
            //注册Flutter plugin
            eventChannelPlugin = EventChannelPlugin.registerWith(getFlutterEngine().getDartExecutor());
            MethodChannelPlugin.registerWith(getFlutterEngine().getDartExecutor(), this);
            basicMessageChannelPlugin = BasicMessageChannelPlugin.registerWith(getFlutterEngine().getDartExecutor(), this);
            uiPresenter = new UIPresenter(this, "通信与混合开发", this);
        } else {
            Log.e(TAG, "getFlutterEngine() is null register plugin fail");
        }
    }

    /**
     * 重载该方法来传递初始化参数
     *
     * @return
     */
    @NonNull
    @Override
    public String getInitialRoute() {
        return initParams == null ? super.getInitialRoute() : initParams;
    }
    //     使用在MyApplication预先初始化好的Flutter引擎以提升Flutter页面打开速度，注意：在这种模式下回导致getInitialRoute 不被调用所以无法设置初始化参数
    //    @Override
    //    public String getCachedEngineId() {
    //        return CACHED_ENGINE_ID;
    //    }

    @Override
    public void onShowMessage(String message) {
        uiPresenter.showDartMessage(message);
    }

    @Override
    public void sendMessage(String message, boolean useEventChannel) {
        if (useEventChannel) {
            eventChannelPlugin.send(message);
        } else {
            basicMessageChannelPlugin.send(message, this::onShowMessage);
        }
    }
}
