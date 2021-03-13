package org.emdev.ui.uimanager;

import android.app.Activity;
import android.view.View;

public interface IUIManager {

//    LogContext LCTX = LogManager.root().lctx("UIManager");

    IUIManager instance = new UIManager40x();

//    void onPause(Activity activity);
//
//    void onResume(Activity activity);

    void setFullScreenMode(Activity activity, View view, boolean fullScreen);

    void onMenuOpened(Activity activity);

    void onMenuClosed(Activity activity);

    boolean isTabletUi(final Activity activity);
}
