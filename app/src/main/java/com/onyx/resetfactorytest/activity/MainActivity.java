package com.onyx.resetfactorytest.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onyx.resetfactorytest.R;
import com.onyx.resetfactorytest.utils.ActivityUtil;
import com.onyx.resetfactorytest.utils.SystemPropertiesUtils;

import java.lang.reflect.Method;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnAutoResetFactory;
    private Button btnAutoReboot;
    private Button btnDictTest;
    private Button btnExit;
    private Button btnUninstall;
    private Button btnInstall;
    private static Method sMethodCopyFile;

    @Override
    protected void createActivity() {
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        btnAutoResetFactory = (Button) findViewById(R.id.btn_auto_factory);
        btnAutoReboot = (Button) findViewById(R.id.btn_auto_reboot);
        btnDictTest = (Button) findViewById(R.id.btn_dict_test);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnUninstall = (Button) findViewById(R.id.btn_remove_app_from_ssytem);
        btnInstall = (Button) findViewById(R.id.btn_install_app_to_ssytem);
    }

    private void initEvent() {
        btnAutoResetFactory.setOnClickListener(this);
        btnAutoReboot.setOnClickListener(this);
        btnDictTest.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnUninstall.setOnClickListener(this);
        btnInstall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auto_factory:
                ActivityUtil.startActivity(this, ResetFactoryActivity.class);
                break;
            case R.id.btn_auto_reboot:
                ActivityUtil.startActivity(this, RebootActivity.class);
                break;
            case R.id.btn_dict_test:
                ActivityUtil.startActivity(this, DictionaryTestActivity.class);
                break;
            case R.id.btn_remove_app_from_ssytem:
                uninstallApkfromSystem();
                break;
            case R.id.btn_install_app_to_ssytem:
                installApk2System();
                break;
            case R.id.btn_exit:
                MainActivity.this.finish();
                break;
        }
    }

    private void uninstallApkfromSystem() {
        if(SystemPropertiesUtils.set("ctl.start", "switch_system_app:uninstall")){
            Toast.makeText(this, "失败！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "成功！", Toast.LENGTH_SHORT).show();
        }
    }

    private void installApk2System() {
        if(SystemPropertiesUtils.set("ctl.start", "switch_system_app:install")){
            Toast.makeText(this, "失败！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "成功！", Toast.LENGTH_SHORT).show();
        }
    }
}

