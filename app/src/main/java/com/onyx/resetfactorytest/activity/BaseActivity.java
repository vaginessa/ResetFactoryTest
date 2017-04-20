package com.onyx.resetfactorytest.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.MenuItem;

import com.onyx.resetfactorytest.R;
import com.onyx.resetfactorytest.Constant;
import com.onyx.resetfactorytest.utils.FileUtils;
import com.onyx.resetfactorytest.utils.SPUtils;

public abstract class BaseActivity extends Activity {
    ActionBar mActionBar;
    public boolean isAutoFactory = false;
    public boolean isAutoReboot = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        createActivity();
    }

    protected abstract void createActivity();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void satrtAutoResetFactory(final Context context) {
        int factoryNum = (Integer) SPUtils.get(context, Constant.AUTO_FACTORY_NUM, 0);
        if (factoryNum > 0) {
            Log.d("===============","================剩余恢复出厂次数===="+factoryNum);
            factoryNum--;
            SPUtils.put(context, Constant.AUTO_FACTORY_NUM, factoryNum);
            FileUtils.copyFileByStream(Constant.DATA_PATH, Constant.EXTERNAL_PATH);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.sendBroadcast(new Intent(Constant.MASTER_CLEAR_BROADCAST));
                }
            }, Constant.DELAY_TIME);
        }else{
            SPUtils.put(context, Constant.IS_AUTO_FACTORY, false);
        }
    }

    public void satrtAutoReboot(final Context context) {
        int rebootNum = (Integer) SPUtils.get(context, Constant.AUTO_REBOOT_NUM, 0);
        if (rebootNum > 0) {
            rebootNum--;
            SPUtils.put(context, Constant.AUTO_REBOOT_NUM, rebootNum);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PowerManager pManager=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
                    pManager.reboot(null);
                }
            }, Constant.DELAY_TIME);

        } else{
            SPUtils.put(this, Constant.IS_AUTO_REBOOT, false);
        }
    }

    public void showDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setIcon(R.drawable.ic_app).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}
