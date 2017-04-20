package com.onyx.resetfactorytest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onyx.resetfactorytest.activity.DictionaryTestActivity;
import com.onyx.resetfactorytest.activity.RebootActivity;
import com.onyx.resetfactorytest.activity.ResetFactoryActivity;
import com.onyx.resetfactorytest.Constant;
import com.onyx.resetfactorytest.utils.ActivityUtil;
import com.onyx.resetfactorytest.utils.FileUtils;
import com.onyx.resetfactorytest.utils.SPUtils;

import java.io.File;

/**
 * Created by jaky on 2017/4/7.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if(Constant.BOOT_COMPLETED_BROADCAST.equals(action)){
            restoreConfig();
            runActivity(context);
        }
    }

    private void runActivity(Context context) {
        if ((Boolean) (SPUtils.get(context, Constant.IS_CHECK_DICTIONARY_FILE, false))) {
            ActivityUtil.startActivity(context, "com.onyx.resetfactorytest", DictionaryTestActivity.class.getName());
        } else if ((Boolean) (SPUtils.get(context, Constant.IS_AUTO_FACTORY, false))) {
            ActivityUtil.startActivity(context, "com.onyx.resetfactorytest", ResetFactoryActivity.class.getName());
        } else if ((Boolean) (SPUtils.get(context, Constant.IS_AUTO_REBOOT, false))) {
            ActivityUtil.startActivity(context, "com.onyx.resetfactorytest", RebootActivity.class.getName());
        }
    }

    private void restoreConfig() {
        File dir =new File(new File(Constant.DATA_PATH).getParent());
        if(!dir.exists()){
            dir.mkdirs();
        }
        File backup =new File(Constant.EXTERNAL_PATH);
        if(backup.exists()){
            FileUtils.copyFileByStream(Constant.EXTERNAL_PATH,Constant.DATA_PATH);
            backup.delete();
        }
    }
}
