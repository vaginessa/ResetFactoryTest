package com.onyx.resetfactorytest.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.onyx.resetfactorytest.Constant;


public class ActivityUtil {
    public static void startActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }


    public static void startActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    public static void startActivity(final Context context, final String packageName, final String className) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, className));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        }, Constant.DELAY_START_ACTIVITY_TIME);
    }
}
