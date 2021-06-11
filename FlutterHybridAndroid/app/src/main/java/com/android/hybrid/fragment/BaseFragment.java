package com.android.hybrid.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Author: 信仰年轻
 * Date: 2021-06-11 15:20
 * Email: hydznsqk@163.com
 * Des:
 */
public abstract class BaseFragment extends Fragment  {
    protected View mLayoutView;

    @LayoutRes
    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutView = inflater.inflate(getLayoutId(), container, false);
        return mLayoutView;
    }

    //检测 宿主 是否还存活
    public boolean isAlive() {
        if (isRemoving() || isDetached() || getActivity() == null) {
            return false;
        }
        return true;
    }

    public void showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
