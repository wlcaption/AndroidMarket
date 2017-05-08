package com.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 保存一些常量数据
 * @author admin
 *
 */
public class SpUtil {
    private static SharedPreferences sp;

    private static final String KEY_SETTINGS = "SYSTEM_SETTINGS";

    private static void initSharedPreferences(Context context) {
        sp = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE);
    }

    public static void putInt(Context context, String key, int value) {
        initSharedPreferences(context);
        sp.edit().putInt(key, value).commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        initSharedPreferences(context);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putString(Context context, String key, String value) {
        initSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        initSharedPreferences(context);
        return sp.getInt(key, 0);
    }

    public static String getString(Context context, String key) {
        initSharedPreferences(context);
        return sp.getString(key, null);
    }

    public static boolean getBoolean(Context context, String key) {
        initSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static boolean isContains(Context context, String key) {
        initSharedPreferences(context);
        return sp.contains(key);
        
    }

    public static void removeSetting(Context context, String key) {
        initSharedPreferences(context);
        sp.edit().remove(key).commit();
    }

}
