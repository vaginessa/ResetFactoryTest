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

public class ResetFactoryActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ResetFactoryActivity.class.getSimpleName();
    private Button btnSettings;
    private Button btnStart;
    private EditText edtResetNum;
    private TextView tvResetLeftNum;

    @Override
    protected void createActivity() {
        setContentView(R.layout.activity_reset_factory);
        initView();
        initData();
    }

    private void initData() {
        edtResetNum.setSelection(edtResetNum.getText().length());
        Object obj = SPUtils.get(this, Constant.AUTO_FACTORY_NUM, 0);
        if (obj != null) {
            int num = (Integer) obj;
            tvResetLeftNum.setText("" + num);
        } else {
            tvResetLeftNum.setText("0");
        }
        updateButtonStatus();
    }

    private void initView() {
        btnSettings = (Button) findViewById(R.id.btn_reset_settings);
        btnStart = (Button) findViewById(R.id.btn_reset_start);
        edtResetNum = (EditText) findViewById(R.id.edt_reset_factory_num);
        tvResetLeftNum = (TextView) findViewById(R.id.tv_reset_factory_left_num);

        btnSettings.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_settings:
                setResetFactoryParams();
                break;
            case R.id.btn_reset_start:
                switchAutoFactoryStatus();
                break;
        }
    }

    private void setResetFactoryParams() {
        int num = 1;
        String str = edtResetNum.getText().toString().trim();
        if (!TextUtils.isEmpty(str)) {
            num = Integer.parseInt(str);
        }
        SPUtils.put(this, Constant.AUTO_FACTORY_NUM, num);
        tvResetLeftNum.setText("" + num);

        Toast.makeText(this, "设置完成！", Toast.LENGTH_SHORT).show();
    }

    private void updateButtonStatus() {
        int factoryNum = (Integer) SPUtils.get(this, Constant.AUTO_FACTORY_NUM, 0);
        if (isAutoFactory && factoryNum > 0) {
            btnStart.setText("停止");
            satrtAutoResetFactory(this);
        } else {
            SPUtils.put(this, Constant.IS_AUTO_FACTORY, false);
            btnStart.setText("开始");
        }
    }

    private void switchAutoFactoryStatus() {
        isAutoFactory = (Boolean) (SPUtils.get(this, Constant.IS_AUTO_FACTORY, false));
        isAutoFactory = !isAutoFactory;
        SPUtils.put(this, Constant.IS_AUTO_FACTORY, isAutoFactory);
        updateButtonStatus();
    }
}
