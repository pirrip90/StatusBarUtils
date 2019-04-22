package com.github.xubo.statusbarutils.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.github.xubo.statusbarutils.StatusBarUtils;

import java.util.List;

/**
 * Author：xubo
 * Time：2019-04-22
 * Description：
 */
public class TabActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private RadioGroup tab_rg;
    public boolean isTransparen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        getSupportActionBar().hide();
        isTransparen = StatusBarUtils.setStatusBarTransparenLight(this);
        fragmentManager = getSupportFragmentManager();

        tab_rg = findViewById(R.id.tab_rg);
        setContent(Tab1Fragment.TAG);
        listener();
    }

    private void listener() {
        tab_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab1_rb:
                        setContent(Tab1Fragment.TAG);
                        break;
                    case R.id.tab2_rb:
                        setContent(Tab2Fragment.TAG);
                        break;
                }
            }
        });
    }

    private void setContent(String fragmentTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        hideFragments(fragments, fragmentTransaction, fragmentTag);
        if (Tab1Fragment.TAG.equals(fragmentTag)) {
            Tab1Fragment fragment = (Tab1Fragment) fragmentManager.findFragmentByTag(Tab1Fragment.TAG);
            if (fragment == null) {
                fragment = new Tab1Fragment();
                fragmentTransaction.add(R.id.tab_fl, fragment, fragmentTag);
            } else {
                fragmentTransaction.show(fragment);
            }
            //字符颜色变深
            StatusBarUtils.setStatusBarLightStatus(TabActivity.this, true);
        } else if (Tab2Fragment.TAG.equals(fragmentTag)) {
            Tab2Fragment fragment = (Tab2Fragment) fragmentManager.findFragmentByTag(Tab2Fragment.TAG);
            if (fragment == null) {
                fragment = new Tab2Fragment();
                fragmentTransaction.add(R.id.tab_fl, fragment, fragmentTag);
            } else {
                fragmentTransaction.show(fragment);
            }
            StatusBarUtils.setStatusBarLightStatus(TabActivity.this, fragment.isStatusBarLightStatus());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragments(List<Fragment> fragments, FragmentTransaction fragmentTransaction, String showTag) {
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            String tag = fragment.getTag();
            if (!showTag.equals(tag)) {
                fragmentTransaction.hide(fragment);
            }
        }
    }
}
