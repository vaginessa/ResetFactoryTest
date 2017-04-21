package com.onyx.resetfactorytest.activity;

import android.view.View;
import android.widget.Button;

import com.onyx.resetfactorytest.R;
import com.onyx.resetfactorytest.utils.ActivityUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnAutoResetFactory;
    private Button btnAutoReboot;
    private Button btnDictTest;
    private Button btnExit;

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
    }

    private void initEvent() {
        btnAutoResetFactory.setOnClickListener(this);
        btnAutoReboot.setOnClickListener(this);
        btnDictTest.setOnClickListener(this);
        btnExit.setOnClickListener(this);
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
            case R.id.btn_set_time:
                
                break;
            case R.id.btn_exit:
                MainActivity.this.finish();
                break;
        }
    }
}

