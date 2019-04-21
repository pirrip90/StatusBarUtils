package com.github.xubo.statusbarutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author：xubo
 * Time：2019-04-21
 * Description：
 */
public class StatusBarUtils {
    /**
     * 设置状态栏颜色
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
            View decorView = window.getDecorView();
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.rgb(255, 53, 67));
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
            decorView.setPadding(0,
                    config.getPixelInsetTop(false),
                    config.getPixelInsetRight(),
                    config.getPixelInsetBottom());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //使状态栏导航栏可绘制
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //清除FLAG_TRANSLUCENT_STATUS
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置状态栏颜色,并且状态栏字符颜色变为深色
     * @param activity
     * @param color
     */
    public static void setStatusBarColorLight(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //状态栏字符颜色为深色
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //使状态栏导航栏可绘制
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //清除FLAG_TRANSLUCENT_STATUS
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置状态栏颜色
                window.setStatusBarColor(color);
            } else if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
                //miui官方兼容修改法
                Class<? extends Window> clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    extraFlagField.invoke(window, true ? darkModeFlag : 0, darkModeFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //使状态栏导航栏可绘制
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //清除FLAG_TRANSLUCENT_STATUS
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置状态栏颜色
                window.setStatusBarColor(color);
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
                //使状态栏导航栏可绘制
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //清除FLAG_TRANSLUCENT_STATUS
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置状态栏颜色
                window.setStatusBarColor(color);
            }
        }
    }

    /**
     * 设置Activity全屏且状态栏透明,状态栏字符可见(例如图片沉浸式)
     * @param activity
     * @return
     */
    public static boolean statusBarTransparen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            return true;
        }
        return false;
    }

    /**
     * 设置Activity全屏且状态栏透明,状态栏字符可见(例如图片沉浸式)
     * @param activity
     * @return
     */
    public static boolean statusBarTransparenLight(Activity activity) {
        OSInfo.OSType osType = OSInfo.getRomType(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setStatusBarColor(Color.TRANSPARENT);
                return true;
            } else if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.setStatusBarColor(Color.TRANSPARENT);
                Class<? extends Window> clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    extraFlagField.invoke(window, true ? darkModeFlag : 0, darkModeFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.setStatusBarColor(Color.TRANSPARENT);
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取状态栏颜色
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = activity.getWindow().getStatusBarColor();
            return color;
        }
        return 0;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //6.0以上状态栏高度默认是24dp,以下则是25dp
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            } else {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            }
        }
    }

    /**
     * 判断状态栏是否隐藏(沉浸式状态栏)
     * @param activity
     * @return
     */
    public static boolean isHideStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {  //4.4及以上状态栏隐藏判断
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {  //4.1以上状态栏隐藏判断
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN属性本质只是把Activity置顶,但状态栏依旧显示(盖在Activity之上,一般配合5.0的)
            //一般5.0及以上会配合window.setStatusBarColor(Color.TRANSPARENT) (状态栏颜色透明)来做沉浸式状态栏
            if (systemUiVisibility == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) || systemUiVisibility == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) || systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否全屏
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {  //4.1及以上全屏判断
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            if (systemUiVisibility == View.SYSTEM_UI_FLAG_FULLSCREEN || systemUiVisibility == View.INVISIBLE) {
                return true;
            }
        }
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {  //通用全屏判断
            return true;
        }
        return false;
    }

}
