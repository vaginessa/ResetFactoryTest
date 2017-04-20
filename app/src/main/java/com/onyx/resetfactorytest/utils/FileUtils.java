package com.onyx.resetfactorytest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by 12345 on 2017/4/7.
 */

public class FileUtils {

    public static List<File> getSubFileList(File path) {
        if (!path.isDirectory()) {
            return null;
        }
        List<File> subFileList = new ArrayList<File>();
        File[] files = path.listFiles();
        if (files == null) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            subFileList.add(files[i]);
        }
        return subFileList;
    }

    public static String getFileType(String fileName) {
        if (fileName != "" && fileName.length() > 3) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0) {
                return fileName.substring(dot + 1);
            }
        }
        return "";
    }

    public static String getSDPath() {
        String sdcard = Environment.getExternalStorageState();
        if (sdcard.equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String getBasePath() {
        String basePath = getSDPath();
        if (basePath == null) {
            return Environment.getDataDirectory().getAbsolutePath();
        } else {
            return basePath;
        }
    }

    public static boolean saveFile(String savePatch, String content) {
        try {
            FileWriter fw = new FileWriter(savePatch);
            fw.flush();
            fw.write(content);
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFileByStream(String oldPath, String newPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            int byteread = 0;
            File oldfile = new File(oldPath.trim());
            if (oldfile.exists()) {
                fis = new FileInputStream(oldPath.trim());
                fos = new FileOutputStream(newPath.trim());
                byte[] buffer = new byte[1024];
                while ((byteread = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }
            } else {
                Log.d("FileUtils", "src file is not exists! src: " + oldPath);
            }
        } catch (Exception e) {
            Log.d("FileUtils", "failed to copy file, src: " + oldPath + ", dest: " + newPath);
            Log.d("FileUtils", e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
