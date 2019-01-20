package org.seniorlaguna.engine.utils;

import android.content.Context;

public class DisplayUtils {

    public static Context context;

    public static void setContext(Context context) {
        DisplayUtils.context = context;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeight() {
        return (DisplayUtils.context == null) ? null : getScreenHeight(DisplayUtils.context);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidth() {
        return (DisplayUtils.context == null) ? null : getScreenWidth(DisplayUtils.context);
    }

    public static int[] getScreenSize(Context context) {
        int size[] = {getScreenHeight(context), getScreenWidth(context)};
        return size;
    }

    public static int[] getScreenSize() {
        return (DisplayUtils.context == null) ? null : getScreenSize(DisplayUtils.context);
    }
}