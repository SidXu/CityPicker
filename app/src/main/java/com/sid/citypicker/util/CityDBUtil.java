package com.sid.citypicker.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by dell on 2016/8/19.
 */
public class CityDBUtil {
    private static final String TAG = "CityDBUtil";

    private static final int DB_VERSION = 1;

    public static String DB_NAME = "cities.db";
    private static final String CITY_TABLE = "cities";
    public static final String PROVINCE_ID = "0";
    private static final String COLUMN_SHORT_NAME = "shortname";
    private static final String COLUMN_AREA_NAME = "areaname";

    private Database database;
    private SQLiteDatabase db;

    public CityDBUtil(Context context) {
        database = new Database(context);
    }

    public CityDBUtil open() throws SQLException {
        db = database.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public Cursor selectByParentId(String id) {
        Cursor cursor = db.query(CITY_TABLE, new String[]{"id", "shortname"}, "parentid=" + id, null, null, null, "id");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public String selectById(String id) {
        String res = "";
        if (TextUtils.isEmpty(id)) {
            return res;
        }
        Cursor cursor = db.query(CITY_TABLE, new String[]{"id", "shortname"}, "id=" + id, null, null, null, "id");
        if (cursor != null && cursor.moveToNext()) {
            res = cursor.getString(cursor.getColumnIndex("shortname"));
            cursor.close();
        }
        return res;
    }

    public String queryShortName(String name) {
        Log.d(TAG, "queryShortName: name=" + name);
        db.beginTransaction();
        Cursor cursor = null;

        String shortName = "";

        try {
            cursor = db.query(CITY_TABLE, new String[]{COLUMN_SHORT_NAME}, COLUMN_AREA_NAME + " LIKE ? and level=2", new String[]{"%" + name + "%"}, null, null, "id asc");
            if (cursor.moveToNext()) {
                shortName = cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_NAME));
                Log.d(TAG, "queryShortName: shortName=" + shortName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        return shortName;
    }

    private class Database extends SQLiteOpenHelper {

        public Database(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

