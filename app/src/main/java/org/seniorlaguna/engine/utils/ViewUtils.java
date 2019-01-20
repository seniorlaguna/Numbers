package org.seniorlaguna.engine.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtils {

    private static View view;
    private static ViewGroup.LayoutParams layoutParams;

    public static void hideView(Activity activity, int viewId) {
        activity.findViewById(viewId).setVisibility(View.INVISIBLE);
    }

    public static void hideViews(Activity activity, int[] viewIds) {
        for (int i = 0; i < viewIds.length; i++) {
            activity.findViewById(viewIds[i]).setVisibility(View.INVISIBLE);
        }
    }

    public static void showView(Activity activity, int viewId) {
        activity.findViewById(viewId).setVisibility(View.VISIBLE);
    }

    public static void showViews(Activity activity, int[] viewIds) {
        for (int i = 0; i < viewIds.length; i++) {
            activity.findViewById(viewIds[i]).setVisibility(View.VISIBLE);
        }
    }

    public static void scaleView(Activity activity, int viewId, int height, int width) {
        view = activity.findViewById(viewId);
        layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    public static void fillTextView(Activity activity, int viewId, String text) {
        ((TextView) activity.findViewById(viewId)).setText(text);
    }
}