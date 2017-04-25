package com.onyx.resetfactorytest;

import com.onyx.resetfactorytest.activity.MainActivity;
import com.onyx.resetfactorytest.utils.FileUtils;

/**
 * Created by jaky on 2017/4/20.
 */

public class Constant {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String FILLNAME = "diactionary_test_config";
    public static final String IS_CHECK_DICTIONARY_FILE = "is_check_dictionary_file";
    public static final String IS_CHECK_MD5 = "is_check_md5";
    public static final String CHECK_FOLD = "check_fold";
    public static final String CHECK_FILE_SUBFIX = "check_file_subfix";

    public static final String IS_AUTO_FACTORY = "is_auto_factory";
    public static final String AUTO_FACTORY_NUM = "auto_factory_num";

    public static final String IS_AUTO_REBOOT = "is_auto_reboot";
    public static final String AUTO_REBOOT_NUM = "auto_reboot_num";

    public static final String EXTERNAL_PATH = FileUtils.getSDPath() + "/" + FILLNAME + ".xml";
    public static final String DATA_PATH = "/data/data/" + Constant.class.getPackage().getName() + "/shared_prefs/" + FILLNAME + ".xml";

    public static final String MASTER_CLEAR_BROADCAST = "android.intent.action.MASTER_CLEAR";
    public static final String BOOT_COMPLETED_BROADCAST = "android.intent.action.BOOT_COMPLETED";

    public static final String COPY_TEST_APK_TO_SYSTEM = "copy_test_apk_to_system";
    public static final String DELETE_TEST_APK_FROM_SYSTEM = "delete_test_apk_from_system";

    public static final int DELAY_START_ACTIVITY_TIME = 3 * 1000;
    public static final int DELAY_TIME = 2 * 1000;
}
