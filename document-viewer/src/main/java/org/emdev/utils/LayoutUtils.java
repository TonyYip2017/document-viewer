package org.emdev.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public final class LayoutUtils {

    public static final int FILL_PARENT = ViewGroup.LayoutParams.FILL_PARENT;

    private LayoutUtils() {
    }

    public static View fillInParent(final View parent, final View view) {
        if (parent instanceof FrameLayout) {
            view.setLayoutParams(new FrameLayout.LayoutParams(FILL_PARENT, FILL_PARENT));
        } else if (parent instanceof LinearLayout) {
            view.setLayoutParams(new LinearLayout.LayoutParams(FILL_PARENT, FILL_PARENT));
        } else if (parent instanceof AbsListView) {
            view.setLayoutParams(new AbsListView.LayoutParams(FILL_PARENT, FILL_PARENT));
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, FILL_PARENT));
        }
        return view;
    }

    public static void maximizeWindow(final Window window) {
        window.setLayout(FILL_PARENT, FILL_PARENT);
    }

// --Commented out by Inspection START (3/10/21 5:15 PM):
//    public static int getDeviceSize(final int dipSize) {
//        return (int) (dipSize * BaseDroidApp.context.getResources().getDisplayMetrics().density + 0.5f);
//    }
// --Commented out by Inspection STOP (3/10/21 5:15 PM)
}
