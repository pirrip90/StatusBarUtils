package com.github.xubo.statusbarutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author：xubo
 * Time：2019-04-21
 * Description：
 */
public class OSInfo {
    public enum OSType {
        OS_TYPE_OTHER(0),
        OS_TYPE_EMUI(1),
        OS_TYPE_MIUI(2),
        OS_TYPE_FLYME(3),
        OS_TYPE_COLOR(4),
        OS_TYPE_FUNTOUCH(5);

        private final int value;

        OSType(int value) {
            this.value = value;
        }
    }

    /** SharedPreferences标识 */
    public static final String OS_SP = "com_github_xubo_statusbarutils_os_sp";

    /** MIUI标识(小米) */
    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    /** EMUI标识(华为) */
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    /** Flyme标识(魅族) */
    public static final String KEY_VERSION_FLYME = "ro.meizu.setupwizard.flyme";
    /** color标识(oppo) */
    private static final String KEY_VERSION_COLOR = "ro.build.version.opporom";
    /** color标识(funtouch) */
    private static final String KEY_VERSION_FUNTOUCH = "ro.vivo.os.version";

    public static OSType getRomType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OS_SP, Context.MODE_PRIVATE);
        int osTypeValue = sharedPreferences.getInt("os_type", -1);
        if (osTypeValue == -1) {
            String display = Build.DISPLAY;
            if (display.toUpperCase().contains("FLYME")) {
                sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FLYME.value).commit();
                return OSType.OS_TYPE_FLYME;
            } else {
                if (!TextUtils.isEmpty(getProp(KEY_VERSION_MIUI))) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_MIUI.value).commit();
                    return OSType.OS_TYPE_MIUI;
                } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_EMUI))) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_EMUI.value).commit();
                    return OSType.OS_TYPE_EMUI;
                } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_FLYME))) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FLYME.value).commit();
                    return OSType.OS_TYPE_FLYME;
                } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_COLOR))) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_COLOR.value).commit();
                    return OSType.OS_TYPE_COLOR;
                } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_FUNTOUCH))) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FUNTOUCH.value).commit();
                    return OSType.OS_TYPE_FUNTOUCH;
                } else {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_OTHER.value).commit();
                    return OSType.OS_TYPE_OTHER;
                }
            }
        } else {
            OSType osType;
            switch (osTypeValue) {
                case 0:
                    osType = OSType.OS_TYPE_OTHER;
                    break;
                case 1:
                    osType = OSType.OS_TYPE_EMUI;
                    break;
                case 2:
                    osType = OSType.OS_TYPE_MIUI;
                    break;
                case 3:
                    osType = OSType.OS_TYPE_FLYME;
                    break;
                case 4:
                    osType = OSType.OS_TYPE_COLOR;
                    break;
                case 5:
                    osType = OSType.OS_TYPE_FUNTOUCH;
                    break;
                default:
                    osType = OSType.OS_TYPE_OTHER;
                    break;
            }
            return osType;
        }
    }

    public static String getProp(String name) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}
