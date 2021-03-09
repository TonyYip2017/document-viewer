package org.emdev.ui.uimanager;

import android.app.Activity;
import android.view.View;

import org.emdev.common.log.LogContext;
import org.emdev.common.log.LogManager;

public interface IUIManager {

    LogContext LCTX = LogManager.root().lctx("UIManager");

    IUIManager instance = new UIManager40x();

//    void onPause(Activity activity);
//
//    void onResume(Activity activity);

    void setFullScreenMode(Activity activity, View view, boolean fullScreen);

    void onMenuOpened(Activity activity);

    void onMenuClosed(Activity activity);

    boolean isTabletUi(final Activity activity);
}
