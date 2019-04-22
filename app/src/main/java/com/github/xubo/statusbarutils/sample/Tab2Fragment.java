package com.github.xubo.statusbarutils.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.xubo.statusbarutils.StatusBarUtils;

/**
 * Author：xubo
 * Time：2019-04-22
 * Description：
 */
public class Tab2Fragment extends Fragment {
    public static final String TAG = Tab2Fragment.class.getSimpleName();
    private View tab2_statusbar_view;
    private LinearLayout tab_title_ll;
    private MyScrollView tab2_sv;
    private boolean isTransparen;
    private TabActivity activity;
    private boolean isLightStatus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (TabActivity) context;
        isTransparen = activity.isTransparen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        tab2_statusbar_view.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(getContext());
        tab2_statusbar_view.setBackgroundColor(Color.WHITE);
        if (isTransparen) {
            tab2_statusbar_view.setVisibility(View.VISIBLE);
        } else {
            tab2_statusbar_view.setVisibility(View.GONE);
        }
        tab_title_ll.measure(0, 0);
        final float height = tab_title_ll.getMeasuredHeight();
        tab_title_ll.setAlpha(0);

        tab2_sv.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void scroll(int y) {
                boolean status = isLightStatus;
                float alpha = 1.0f;
                if (y < height) {
                    alpha = y / height;
                }
                if (alpha < 0.5f) {
                    status = false;
                } else {
                    status = true;
                }
                tab_title_ll.setAlpha(alpha);
                if (status != isLightStatus) {
                    isLightStatus = status;
                    StatusBarUtils.setStatusBarLightStatus(activity, isLightStatus);
                }
            }
        });
    }

    public boolean isStatusBarLightStatus() {
        return isLightStatus;
    }

    private void initView(View view) {
        tab2_statusbar_view = view.findViewById(R.id.tab2_statusbar_view);
        tab2_sv = view.findViewById(R.id.tab2_sv);
        tab_title_ll = view.findViewById(R.id.tab_title_ll);
    }
}
