package com.joltimate.umdmap.other;

import android.util.Log;

/**
 * Created by Chris on 7/12/2015.
 */
public class DebuggingTools {
    public static final boolean ISDEBUG = true;
    private static String defaultClassName = "DebuggingTools";

    public static void logd(String className, String message) {
        if (ISDEBUG) {
            Log.d(className, message);
        }
    }
    public static void logd(String message) {
        if (ISDEBUG) {
            Log.d(defaultClassName, message);
        }
    }
}
