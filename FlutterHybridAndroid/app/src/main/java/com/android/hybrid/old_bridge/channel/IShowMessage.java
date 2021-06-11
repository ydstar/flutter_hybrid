package com.android.hybrid.old_bridge.channel;


/**
 * Flutter  Native通信 | 混合开发
 */
public interface IShowMessage {
    void onShowMessage(String message);
    void sendMessage(String message, boolean useEventChannel);
}
