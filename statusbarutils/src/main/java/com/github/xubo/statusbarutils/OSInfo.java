package com.github.xubo.statusbarutils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

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
    public static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    public static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    public static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    /** EMUI标识(华为) */
    public static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    public static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    public static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    /** Flyme标识(魅族) */
    public static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    public static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    public static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    public static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    public static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";

    /** color标识(oppo) */
    public static final String KEY_VERSION_OPPO_CODE = "ro.build.version.opporom";

    /** color标识(funtouch) */
    public static final String KEY_VERSION_VIVO_CODE = "ro.vivo.os.version";

    public static OSType getRomType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OS_SP, Context.MODE_PRIVATE);
        int osTypeValue = sharedPreferences.getInt("os_type", -1);
        if (osTypeValue == -1) {
            try {
                BuildProperties buildProperties = BuildProperties.getInstance();
                if (buildProperties.containsKey(KEY_EMUI_VERSION_CODE) || buildProperties.containsKey(KEY_EMUI_API_LEVEL) || buildProperties.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_EMUI.value).commit();
                    return OSType.OS_TYPE_EMUI;
                }
                if (buildProperties.containsKey(KEY_MIUI_VERSION_CODE) || buildProperties.containsKey(KEY_MIUI_VERSION_NAME) || buildProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_MIUI.value).commit();
                    return OSType.OS_TYPE_MIUI;
                }
                if (buildProperties.containsKey(KEY_FLYME_ICON_FALG) || buildProperties.containsKey(KEY_FLYME_SETUP_FALG) || buildProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FLYME.value).commit();
                    return OSType.OS_TYPE_FLYME;
                }
                if (buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                    String romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY);
                    if (romName != null && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                        sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FLYME.value).commit();
                        return OSType.OS_TYPE_FLYME;
                    }
                }
                if (buildProperties.containsKey(KEY_VERSION_OPPO_CODE)) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_COLOR.value).commit();
                    return OSType.OS_TYPE_COLOR;
                }
                if (buildProperties.containsKey(KEY_VERSION_VIVO_CODE)) {
                    sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_FUNTOUCH.value).commit();
                    return OSType.OS_TYPE_FUNTOUCH;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sharedPreferences.edit().putInt("os_type", OSType.OS_TYPE_OTHER.value).commit();
            return OSType.OS_TYPE_OTHER;
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
}
