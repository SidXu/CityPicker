package com.sid.citypicker;

import android.app.Application;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.sid.citypicker.util.AppUtil;
import com.sid.citypicker.util.CityDBUtil;
import com.sid.citypicker.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sid on 2017/8/1.
 */

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    private static String DB_PATH;

    @Override
    public void onCreate() {
        super.onCreate();

        createDataBase();
    }

    // 创建城市数据库
    public void createDataBase() {
        String dir = getFilesDir().getPath();
        DB_PATH = dir.substring(0, dir.lastIndexOf("/")) + "/databases/";

        File file = new File(DB_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        String currentDBName = AppUtil.getDatabaseNameFromPath(DB_PATH);
        int currentDBVersion = AppUtil.getDatabaseFileVersion(currentDBName);
        CityDBUtil.DB_NAME = currentDBName;

        String newDBName = AppUtil.getDatabaseNameFromAssets(getApplicationContext());
        int newDBVersion = AppUtil.getDatabaseFileVersion(newDBName);
        boolean isValid = checkDataBaseSizeValid(newDBName, currentDBName);
        if ((newDBVersion > currentDBVersion) || !isValid) {
            copyDataBase(newDBName, currentDBName);
        }
    }

    private boolean checkDataBaseSizeValid(String name, String oldName) {
        boolean isValid = false;
        // assets下城市数据库大小
        AssetManager assetManager = this.getAssets();
        long total = 0;
        long oldSize = 0;
        try {
            InputStream inputStream = assetManager.open(name);
            byte[] buffer = new byte[4096];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                total += length;
            }
        } catch (Throwable e) {
            Log.e(TAG, "checkDataBaseSizeValid: e=" + e.getMessage());
        }

        // 获取data/data下城市数据库大小
        if (!StringUtil.emptyString(oldName)) {
            File file = new File(DB_PATH + oldName);
            if (file.exists()) {
                oldSize = file.length();
            }
        }
        if (total <= oldSize) {
            isValid = true;
        }
        return isValid;
    }

    private void copyDataBase(final String name, final String oldName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = getApplicationContext().getAssets().open(name);
                    String fileName = DB_PATH + name;
                    outputStream = new FileOutputStream(fileName);
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.flush();

                    if (!TextUtils.equals(name, oldName)) {
                        deleteDatabase(oldName);
                    }

                } catch (Exception e) {
                    Log.e(TAG, "copyDataBase: error message:" + e.getMessage());
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }

                        if (outputStream != null) {
                            outputStream.close();
                            outputStream = null;
                        }

                    } catch (Exception e1) {
                        Log.e(TAG, "copyDataBase: error e1=" + e1.getMessage());
                    }
                }

                CityDBUtil.DB_NAME = name;
            }
        }).start();
    }
}
