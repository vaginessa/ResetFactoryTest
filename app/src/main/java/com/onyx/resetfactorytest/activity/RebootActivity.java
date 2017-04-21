package com.onyx.resetfactorytest.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onyx.resetfactorytest.R;
import com.onyx.resetfactorytest.Constant;
import com.onyx.resetfactorytest.utils.SPUtils;

public class RebootActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = RebootActivity.class.getSimpleName();
    private Button btnSettings;
    private Button btnStart;
    private EditText edtRebootNum;
    private TextView tvRebootLeftNum;

    @Override
    protected void createActivity() {
        setContentView(R.layout.activity_reboot);
        initView();
        initData();
    }

    private void initView() {
        btnSettings = (Button) findViewById(R.id.btn_reboot_setting);
        btnStart = (Button) findViewById(R.id.btn_start);
        edtRebootNum = (EditText) findViewById(R.id.edt_reboot_num);
        tvRebootLeftNum = (TextView) findViewById(R.id.tv_reboot_left_num);

        btnSettings.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    private void initData() {
        edtRebootNum.setSelection(edtRebootNum.getText().length());
        Object obj = SPUtils.get(this, Constant.AUTO_REBOOT_NUM, 0);
        if (obj != null) {
            int num = (Integer) obj;
            tvRebootLeftNum.setText("" + num);
        } else {
            tvRebootLeftNum.setText("0");
        }
        updateButtonStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reboot_setting:
                setRebootParams();
                break;
            case R.id.btn_start:
                switchAutoRebootStatus();
                break;
        }
    }

    private void switchAutoRebootStatus() {
        isAutoReboot = (Boolean) (SPUtils.get(this, Constant.IS_AUTO_REBOOT, false));
        isAutoReboot = !isAutoReboot;
        SPUtils.put(this, Constant.IS_AUTO_REBOOT, isAutoReboot);
        updateButtonStatus();
    }

    private void setRebootParams() {
        int num = 0;
        String str = edtRebootNum.getText().toString().trim();
        if (!TextUtils.isEmpty(str)) {
            num = Integer.parseInt(str);
        }
        SPUtils.put(this, Constant.AUTO_REBOOT_NUM, num);
        tvRebootLeftNum.setText("" + num);

        Toast.makeText(this, "设置完成！", Toast.LENGTH_SHORT).show();
    }

    private void updateButtonStatus() {
        int rebootNum = (Integer) SPUtils.get(this, Constant.AUTO_REBOOT_NUM, 0);
        if (isAutoReboot && rebootNum > 0) {
            btnStart.setText("停止");
            satrtAutoReboot(this);
        } else {
            btnStart.setText("开始");
        }
    }
}
