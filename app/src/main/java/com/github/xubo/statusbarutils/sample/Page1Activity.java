package com.github.xubo.statusbarutils.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.xubo.statusbarutils.StatusBarUtils;

/**
 * Author：xubo
 * Time：2019-04-22
 * Description：
 */
public class Page1Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        StatusBarUtils.setStatusBarColor(this, Color.parseColor("#FFCC0000"));
    }
}
