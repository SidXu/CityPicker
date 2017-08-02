package com.sid.citypicker.util;

/**
 * Created by Sid on 2017/8/1.
 */

public class StringUtil {

    public static boolean emptyString(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static String safeString(String string) {
        return string != null?string:"";
    }
}
