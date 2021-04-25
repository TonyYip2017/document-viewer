package org.emdev;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.emdev.common.log.LogManager;
import org.emdev.utils.FileUtils;
import org.emdev.utils.LengthUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseDroidApp extends Application {

    public static Context context;

    private static String APP_VERSION_NAME;
    public static String APP_PACKAGE;
    public static File EXT_STORAGE;
    public static File APP_STORAGE;
    public static String APP_NAME;
    private static Locale defLocale;
    private static Locale appLocale;
    private static String APP_INFO;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.instance);
        this.init();
        LogManager.init(this);
    }

    protected void init() {
        context = getApplicationContext();

        final Configuration config = context.getResources().getConfiguration();
        appLocale = defLocale = config.locale;

        final PackageManager pm = getPackageManager();
        try {
            final PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            APP_NAME = getString(pi.applicationInfo.labelRes);
            int APP_VERSION_CODE = pi.versionCode;
            APP_VERSION_NAME = LengthUtils.safeString(pi.versionName, "DEV");
            APP_PACKAGE = pi.packageName;
            EXT_STORAGE = Environment.getExternalStorageDirectory();
            APP_STORAGE = getExternalFilesDir(null);
            APP_INFO = APP_NAME + " (" + APP_PACKAGE + ") " + APP_VERSION_NAME + "(" + APP_VERSION_CODE + ")\n" +
                    "External storage dir: " + EXT_STORAGE + "\n" +
                    "App      storage dir: " + APP_STORAGE + "\n" +
                    "Files            dir: " + FileUtils.getAbsolutePath(getFilesDir()) + "\n" +
                    "Cache            dir: " + FileUtils.getAbsolutePath(getCacheDir()) + "\n" +
                    "System locale       : " + defLocale + "\n\n" +
                    "VERSION     : " + Build.VERSION.SDK_INT + "\n" +
                    "DEVICE      : " + Build.DEVICE + "\n" +
                    "DISPLAY     : " + Build.DISPLAY;
            Log.i(APP_NAME, APP_INFO);
        } catch (final NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        final Configuration oldConfig = getResources().getConfiguration();
        final int diff = oldConfig.diff(newConfig);
        final Configuration target = diff == 0 ? oldConfig : newConfig;

        if (appLocale != null) {
            setAppLocaleIntoConfiguration(target);
        }
        super.onConfigurationChanged(target);
    }

    public static void setAppLocale(final String lang) {
        final Configuration config = context.getResources().getConfiguration();
        appLocale = LengthUtils.isNotEmpty(lang) ? new Locale(lang) : defLocale;
        setAppLocaleIntoConfiguration(config);
    }

    protected static void setAppLocaleIntoConfiguration(final Configuration config) {
        if (!config.locale.equals(appLocale)) {
            Locale.setDefault(appLocale);
            config.locale = appLocale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        Log.i(APP_NAME, "UI Locale: " + appLocale);
    }

    public static void logThrowable(final Throwable ex) {
        final File logPath = new File(APP_STORAGE, "log");
        logPath.mkdirs();
        try (@SuppressLint("SimpleDateFormat")
             PrintWriter writer = new PrintWriter(new File(logPath,
                BaseDroidApp.APP_PACKAGE + "." +
                BaseDroidApp.APP_VERSION_NAME + "." +
                new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date()) +
                ".stacktrace"))) {
            writer.print(
                    "Application information:\n\n" +
                    BaseDroidApp.APP_INFO + "\n\n" +
                    "\nError information:\n\n");
            ex.printStackTrace(writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        public static final ExceptionHandler instance = new ExceptionHandler();
        Thread.UncaughtExceptionHandler system = Thread.getDefaultUncaughtExceptionHandler();

        @Override
        public void uncaughtException(final Thread thread, final Throwable ex) {
            BaseDroidApp.logThrowable(ex);
            system.uncaughtException(thread, ex);
        }
    }
}
