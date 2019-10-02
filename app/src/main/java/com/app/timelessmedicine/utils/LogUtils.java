package com.app.timelessmedicine.utils;

import android.util.Log;
import com.app.timelessmedicine.BuildConfig;

/**
 * Created by fluper on 22/3/18.
 */

public class LogUtils {
    public static void log(String tag, String value) {

        if (BuildConfig.DEBUG) {
            Log.d(String.valueOf(tag), String.valueOf(value));
        }
    }

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(String.valueOf("REST"), String.valueOf(message));
        }
    }



}
