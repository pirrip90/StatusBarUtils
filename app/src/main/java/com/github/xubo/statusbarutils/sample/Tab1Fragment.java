package com.github.xubo.statusbarutils.sample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.xubo.statusbarutils.StatusBarUtils;

/**
 * Author：xubo
 * Time：2019-04-22
 * Description：
 */
public class Tab1Fragment extends Fragment {
    public static final String TAG = Tab1Fragment.class.getSimpleName();
    private View statusbar_view;
    private boolean isTransparen;
    private TabActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (TabActivity) context;
        isTransparen = activity.isTransparen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        statusbar_view.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(getContext());
        statusbar_view.setBackgroundColor(Color.WHITE);
        if (isTransparen) {
            statusbar_view.setVisibility(View.VISIBLE);
        } else {
            statusbar_view.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        statusbar_view = view.findViewById(R.id.statusbar_view);
    }
}
