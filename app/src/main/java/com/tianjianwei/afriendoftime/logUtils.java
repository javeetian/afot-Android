package com.tianjianwei.afriendoftime;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by tianjianwei20 on 2017/6/8.
 */

public class logUtils {

    /**
     * 打印调试级别日志
     *
     * @param format
     * @param args
     */
    public static void logDebug(String format, Object... args) {
        logMessage(Log.DEBUG, format, args);
    }

    /**
     * 打印信息级别日志
     *
     * @param format
     * @param args
     */
    public static void logInfo(String format, Object... args) {
        logMessage(Log.INFO, format, args);
    }

    /**
     * 打印错误级别日志
     *
     * @param format
     * @param args
     */
    public static void logError(String format, Object... args) {
        logMessage(Log.ERROR, format, args);
    }

    /**
     * 展示短时Toast
     *
     * @param format
     * @param args
     */
    public static void showShortToast(String format, Object... args) {
        showToast(Toast.LENGTH_SHORT, format, args);
    }

    /**
     * 展示长时Toast
     *
     * @param format
     * @param args
     */
    public static void showLongToast(String format, Object... args) {
        showToast(Toast.LENGTH_LONG, format, args);
    }

    /**
     * 打印日志
     *
     * @param level
     * @param format
     * @param args
     */
    public static void logMessage(int level, String format, Object... args) {
        String formattedString = String.format(format, args);
        switch (level) {
            case Log.DEBUG:
                Log.d("log", formattedString);
                break;
            case Log.INFO:
                Log.i("log", formattedString);
                break;
            case Log.ERROR:
                Log.e("log", formattedString);
                break;
        }
    }

    /**
     * 展示Toast
     *
     * @param duration
     * @param format
     * @param args
     */
    public static void showToast(int duration, String format, Object... args) {
        //Toast.makeText(mContext, String.format(format, args), duration).show();
    }
}
