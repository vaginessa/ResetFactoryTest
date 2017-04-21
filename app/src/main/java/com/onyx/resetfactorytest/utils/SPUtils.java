package com.onyx.resetfactorytest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.onyx.resetfactorytest.Constant;

import java.util.Map;

/**
 * Created by 12345 on 2017/4/11.
 */

public class SPUtils {

    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(Constant.FILLNAME, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        edit.apply();
    }

    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(Constant.FILLNAME, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.FILLNAME, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        return sp.getAll();
    }


    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constant.FILLNAME, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.apply();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.FILLNAME, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }

}
