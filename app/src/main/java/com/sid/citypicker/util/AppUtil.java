package com.sid.citypicker.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sid.citypicker.bean.CityFilterBean;
import com.sid.citypicker.config.AppConfig;
import com.sid.citypicker.picker.CityFilterActivity;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class AppUtil {
    private static final String TAG = "AppUtil";

    public static String getDatabaseNameFromPath(String path) {
        if (path != null) {
            try {
                File file = new File(path);
                if (file.isDirectory()) {
                    File[] childList = file.listFiles();
                    for (File one : childList) {
                        String child = one.getName();
                        if (child.startsWith(AppConfig.DB_NAME) && child.endsWith(AppConfig.DB_PREFIX)) {
                            return child;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "getDatabaseNameFromPath: e=" + e.getMessage());
            }
        }
        return "";
    }

    public static String getDatabaseNameFromAssets(Context context) {
        if (context != null) {
            try {
                String[] files = context.getAssets().list("");
                for (String s : files) {
                    if (s.startsWith(AppConfig.DB_NAME) && s.endsWith(AppConfig.DB_PREFIX)) {
                        return s;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "getDatabaseNameFromAssets: e=" + e.getMessage());
            }
        }
        return "";
    }

    public static int getDatabaseFileVersion(String fileName) {
        if (fileName != null) {
            int start = fileName.indexOf(AppConfig.DB_NAME) + AppConfig.DB_NAME.length() + 1;
            int end = fileName.indexOf(AppConfig.DB_PREFIX) - 1;

            try {
                if (start > 0 && end > start) {
                    String id = fileName.substring(start, end);
                    return Integer.valueOf(id);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "getDatabaseFileVersion: e=" + e.getMessage());
            }
        }
        return 0;
    }

    public static CityFilterBean getFilterBean(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Serializable serializable = bundle.getSerializable(CityFilterActivity.DATA_KEY);
        if (serializable == null) {
            return null;
        }
        return (CityFilterBean) serializable;
    }
}
