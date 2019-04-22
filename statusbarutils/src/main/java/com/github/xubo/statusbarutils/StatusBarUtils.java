package com.github.xubo.statusbarutils;

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
    /** oppo coloros 5.1系统该flag标记状态栏字符变深 */
    private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

    /**
     * 设置状态栏颜色
     * 支持4.4以上的所有机型
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
        }
    }

    /**
     * 设置状态栏颜色,并且状态栏字符颜色变为深
     * 支持6.0以上的普通机型
     * 支持5.1以上的oppo机型
     * 支持4.4以上的小米机型
     * 支持4.4以上的魅族机型
     * @param activity
     * @param color
     */
    public static void setStatusBarColorLight(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            } else if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
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
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            } else if (osType == OSInfo.OSType.OS_TYPE_COLOR && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
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
                WindowManager.LayoutParams winParams = window.getAttributes();
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(Color.rgb(255, 53, 67));
                SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
                decorView.setPadding(0,
                        config.getPixelInsetTop(false),
                        config.getPixelInsetRight(),
                        config.getPixelInsetBottom());
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
                WindowManager.LayoutParams winParams = window.getAttributes();
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(Color.rgb(255, 53, 67));
                SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
                decorView.setPadding(0,
                        config.getPixelInsetTop(false),
                        config.getPixelInsetRight(),
                        config.getPixelInsetBottom());
            }
        }
    }

    /**
     * 设置状态栏沉浸式
     * 支持4.4以上的所有机型
     * @param activity
     * @return 设置成功true, 设置失败false
     */
    public static boolean setStatusBarTransparen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
            return true;
        }
        return false;
    }

    /**
     * 设置状态栏沉浸式,并且状态栏字符颜色变为深
     * 支持6.0以上的普通机型
     * 支持5.1以上的oppo机型
     * 支持4.4以上的小米机型
     * 支持4.4以上的魅族机型
     * @param activity
     * @return 设置成功true, 设置失败false
     */
    public static boolean setStatusBarTransparenLight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
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
            } else if (osType == OSInfo.OSType.OS_TYPE_COLOR && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT);
                window.setStatusBarColor(Color.TRANSPARENT);
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
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
                WindowManager.LayoutParams winParams = window.getAttributes();
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
                WindowManager.LayoutParams winParams = window.getAttributes();
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
            }
        }
        return false;
    }

    /**
     * 设置状态栏字符颜色是否变为深
     * 支持6.0以上的普通机型
     * 支持5.1以上的oppo机型
     * 支持所有小米机型
     * 支持所有魅族机型
     * @param activity
     * @param isLight
     */
    public static void setStatusBarLightStatus(Activity activity, boolean isLight) {
        if (isLight) {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(systemUiVisibility);
            } else if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
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
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
            } else if (osType == OSInfo.OSType.OS_TYPE_COLOR && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                systemUiVisibility |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                decorView.setSystemUiVisibility(systemUiVisibility);
            }
        } else {
            OSInfo.OSType osType = OSInfo.getRomType(activity);
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(systemUiVisibility);
            } else if (osType == OSInfo.OSType.OS_TYPE_MIUI) {
                Class<? extends Window> clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    extraFlagField.invoke(window, false ? darkModeFlag : 0, darkModeFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (osType == OSInfo.OSType.OS_TYPE_FLYME) {
                FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, false);
            } else if (osType == OSInfo.OSType.OS_TYPE_COLOR && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                systemUiVisibility &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                decorView.setSystemUiVisibility(systemUiVisibility);
            }
        }
    }

    /**
     * 获取状态栏颜色
     * 5.0以上所有机型可以正常获取,5.0以下机型统一返回黑色
     * @param activity
     * @return
     */
    public static int getStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = activity.getWindow().getStatusBarColor();
            return color;
        } else {
            return Color.BLACK;
        }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            } else {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            }
        }
    }
}
