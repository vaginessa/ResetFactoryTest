package com.onyx.resetfactorytest.utils;

import java.lang.reflect.Method;

/**
 * Created by 12345 on 2017/4/25.
 */

public class SystemPropertiesUtils {

    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;
    private static Method mSetMethod = null;

    private static void init() {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");

                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
                mSetMethod = mClassType.getDeclaredMethod("set", String.class, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean set(final String key, final String value) {
        init();
        try {
            mSetMethod.invoke(mClassType, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String get(final String key, final String defaultValue) {
        init();
        String value = defaultValue;

        try {
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
