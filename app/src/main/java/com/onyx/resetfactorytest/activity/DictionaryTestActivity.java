package com.onyx.resetfactorytest.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onyx.resetfactorytest.R;
import com.onyx.resetfactorytest.Constant;
import com.onyx.resetfactorytest.request.AsyncRequest;
import com.onyx.resetfactorytest.request.RequestCallback;
import com.onyx.resetfactorytest.utils.FileUtils;
import com.onyx.resetfactorytest.utils.SPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryTestActivity extends BaseActivity implements View.OnClickListener {

    private String checkPath = "";
    private String checkFold = "dictionary";
    private List<String> errInfo;
    private List<String> subfixList;
    private List<String> checkList;
    private List<String> tempList;
    private TextView tvInfo;
    private TextView tvLeftNum;
    private Button btnSvaeReport;
    private Button btnControl;
    private Button btnExit;
    private Button btnAutoFactory;
    private Button btnSetFactoryNum;
    private boolean isCheck = false;
    private boolean isCheckMD5 = false;
    private EditText edtFileFormat;
    private EditText edtFilePath;
    private EditText edtNum;
    private CheckBox cbCheckMD5;

    @Override
    protected void createActivity() {
        setContentView(R.layout.activity_dictionary_test);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        cbCheckMD5 = (CheckBox) findViewById(R.id.cb_check_md5);
        edtFileFormat = (EditText) findViewById(R.id.edt_file_format);
        edtFilePath = (EditText) findViewById(R.id.edt_file_path);
        edtNum = (EditText) findViewById(R.id.edt_num);
        tvInfo = (TextView) findViewById(R.id.warm_info);
        tvLeftNum = (TextView) findViewById(R.id.edt_left_num);
        btnControl = (Button) findViewById(R.id.dict_test_control);
        btnSvaeReport = (Button) findViewById(R.id.save_report);
        btnExit = (Button) findViewById(R.id.exit);
        btnAutoFactory = (Button) findViewById(R.id.auto_factory);
        btnSetFactoryNum = (Button) findViewById(R.id.btn_set);
        btnSvaeReport.setVisibility(View.GONE);
    }

    private void initEvent() {
        btnControl.setOnClickListener(this);
        btnSvaeReport.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnAutoFactory.setOnClickListener(this);
        btnSetFactoryNum.setOnClickListener(this);
    }

    private void initData() {
        errInfo = new ArrayList<String>();
        checkList = new ArrayList<String>();
        tempList = new ArrayList<String>();
        subfixList = new ArrayList<String>();
        String[] formatArray = null;
        String filePath = (String) SPUtils.get(this, Constant.CHECK_FOLD, null);

        if (!TextUtils.isEmpty(filePath)) {
            checkFold = filePath;
        } else {
            checkFold = "dictionary";
        }
        edtFilePath.setText(checkFold);

        String fileFormat = (String) SPUtils.get(this, Constant.CHECK_FILE_SUBFIX, null);
        if (!TextUtils.isEmpty(fileFormat)) {
            formatArray = fileFormat.split(",");
        }
        if (formatArray != null) {
            checkList = Arrays.asList(formatArray);
            checkList.remove(0);
            checkList.remove(formatArray.length-1);
        } else {
            checkList.add("dz");
            checkList.add("ifo");
            checkList.add("idx");
            checkList.add("yaidx");
        }
        edtFileFormat.setText(checkList.toString());

        Object obj = SPUtils.get(this, Constant.AUTO_FACTORY_NUM, 0);
        if (obj != null) {
            int num = (Integer) obj;
            tvLeftNum.setText("" + num);
        } else {
            tvLeftNum.setText("0");
        }

        edtFileFormat.setSelection(edtFileFormat.getText().length());
        edtFilePath.setSelection(edtFilePath.getText().length());
        edtNum.setSelection(edtNum.getText().length());

        isCheckMD5 = (Boolean) SPUtils.get(this, Constant.IS_CHECK_MD5, false);
        cbCheckMD5.setSelected(isCheckMD5);

        isCheck = (Boolean) (SPUtils.get(this, Constant.IS_CHECK_DICTIONARY_FILE, false));
        isAutoFactory = (Boolean) (SPUtils.get(this, Constant.IS_AUTO_FACTORY, false));
        updateButtonStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dict_test_control:
                switchTestStatus();
                break;
            case R.id.exit:
                DictionaryTestActivity.this.finish();
                break;
            case R.id.auto_factory:
                switchAutoFactoryStatus();
                break;
            case R.id.btn_set:
                setCheckParams();
                break;
            case R.id.save_report:
                saveReport();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void checkFile() {
        new AsyncRequest<List<String>>(AsyncRequest.DEFAULT_REQUEST, new RequestCallback<List<String>>() {
            @Override
            public void onStart() {
                checkPath = FileUtils.getBasePath() + "/" + checkFold;
                btnSvaeReport.setVisibility(View.GONE);
                btnControl.setEnabled(false);
            }

            @Override
            public List<String> onDoInBackground() {
                doCheckFileIsLost(checkPath);
                return null;
            }

            @Override
            public void onResult(List<String> list) {
                showResult();
                btnControl.setEnabled(true);
                btnSvaeReport.setVisibility(View.VISIBLE);
            }
        }).execute();
    }

    private void doCheckFileIsLost(String filePath) {
        File dictDir = new File(filePath);
        if (!dictDir.exists()) {
            errInfo.add(filePath + "目录不存在！");
            return;
        }
        List<File> dictSubFileList = FileUtils.getSubFileList(new File(filePath));
        if (dictSubFileList != null) {
            int fileCount = 0;
            boolean hasMD5File = false;
            for (File file : dictSubFileList) {
                if (file.isDirectory()) {
                    fileCount++;
                    subfixList.clear();
                    tempList.clear();
                    tempList.addAll(checkList);
                    List<File> subFileList = FileUtils.getSubFileList(file);
                    if (subFileList != null) {
                        for (File sub : subFileList) {
                            if (!sub.isDirectory()) {
                                String subfix = FileUtils.getFileType(sub.getName());
                                if (checkList.contains(subfix) && !subfixList.contains(subfix)) {
                                    subfixList.add(subfix);
                                }
                            }
                        }
                    } else {
                        errInfo.add(file.getName() + "下缺少" + checkList.toString() + "文件！");
                    }
                    if (subfixList.size() < 4) {
                        tempList.removeAll(subfixList);
                        errInfo.add(file.getName() + "下缺少" + tempList.toString() + " 文件！");
                    }

                } else {
                    if ("md5".equals(FileUtils.getFileType(file.getName()))) {
                        hasMD5File = true;
                    }
                }
            }
            if (fileCount < 4) {
                errInfo.add(checkPath + "下的字典文件夹数量少于4个！");
            }
            if (!hasMD5File && isCheckMD5) {
                errInfo.add(checkPath + "下缺少 *.md5 文件！");
            }
            fileCount = 0;
            hasMD5File = false;
        } else {
            errInfo.add(filePath + "下没有任何文件！");
        }
    }

    private void showResult() {
        String msg = "";
        if (errInfo.size() == 0) {
            msg += "All is OK!";
            this.setResult(Activity.RESULT_OK, new Intent());
            satrtAutoResetFactory(this);
        } else {
            this.setResult(Activity.RESULT_CANCELED, new Intent());
            msg += "以下是丢失的字典文件：\n";
            for (String str : errInfo) {
                msg += "\n";
                msg += str;
                msg += "\n";
            }
            showDialog(this,"字典文件丢失检测报告：", msg);
        }
        tvInfo.setText(msg);
    }

    private void setCheckParams() {

        String filePath = edtFilePath.getText().toString().trim();
        if (!TextUtils.isEmpty(filePath)) {
            checkFold = filePath;
        } else {
            checkFold = "dictionary";
        }
        SPUtils.put(this, Constant.CHECK_FOLD, checkFold);

        String fileFormat = edtFileFormat.getText().toString().trim();
        String[] formatArray = null;
        if (!TextUtils.isEmpty(fileFormat)) {
            formatArray = fileFormat.split(",");
        }
        SPUtils.put(this, Constant.CHECK_FILE_SUBFIX, fileFormat);

        isCheckMD5 = cbCheckMD5.isSelected() ? true : false;
        SPUtils.put(this, Constant.IS_CHECK_MD5, isCheckMD5);

        int num = 1;
        String str = edtNum.getText().toString().trim();
        if (!TextUtils.isEmpty(str)) {
            num = Integer.parseInt(str);
        }
        SPUtils.put(this, Constant.AUTO_FACTORY_NUM, num);
        tvLeftNum.setText("" + num);

        Toast.makeText(this, "设置完成！", Toast.LENGTH_SHORT).show();
    }

    private void updateButtonStatus() {
        if (isCheck) {
            btnControl.setText("关闭文件开机自动检测");
            checkFile();
        } else {
            btnControl.setText("开启文件开机自动检测");
        }
        if (isAutoFactory) {
            btnAutoFactory.setText("关闭自动恢复出厂设置");
        } else {
            btnAutoFactory.setText("开启自动恢复出厂设置");
        }
    }

    private void switchAutoFactoryStatus() {
        isAutoFactory = (Boolean) (SPUtils.get(this, Constant.IS_AUTO_FACTORY, false));
        isAutoFactory = !isAutoFactory;
        SPUtils.put(this, Constant.IS_AUTO_FACTORY, isAutoFactory);
        updateButtonStatus();
    }

    private void switchTestStatus() {
        isCheck = (Boolean) (SPUtils.get(this, Constant.IS_CHECK_DICTIONARY_FILE, false));
        isCheck = !isCheck;
        SPUtils.put(this, Constant.IS_CHECK_DICTIONARY_FILE, isCheck);
        updateButtonStatus();
    }

    private void saveReport() {
        if (FileUtils.saveFile(FileUtils.getBasePath() + "/DictTestReport.txt", tvInfo.getText().toString())) {
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
        }
    }
}

