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

    public static String APP_VERSION_NAME;
    public static String APP_PACKAGE;
    public static File EXT_STORAGE;
    public static File APP_STORAGE;
    public static String APP_NAME;
    public static Locale defLocale;
    private static Locale appLocale;
    public static String APP_INFO;

    /**
     * {@inheritDoc}
     *
     * @see android.app.Application#onCreate()
     */
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

//            Log.i(APP_NAME, APP_NAME + " (" + APP_PACKAGE + ")" + " " + APP_VERSION_NAME + "(" + pi.versionCode + ")");
////            Log.i(APP_NAME, "Root             dir: " + Environment.getRootDirectory());
////            Log.i(APP_NAME, "Data             dir: " + Environment.getDataDirectory());
//            Log.i(APP_NAME, "External storage dir: " + EXT_STORAGE);
//            Log.i(APP_NAME, "App      storage dir: " + APP_STORAGE);
//            Log.i(APP_NAME, "Files            dir: " + FileUtils.getAbsolutePath(getFilesDir()));
//            Log.i(APP_NAME, "Cache            dir: " + FileUtils.getAbsolutePath(getCacheDir()));
//            Log.i(APP_NAME, "System locale       : " + defLocale);
//            Log.i(APP_NAME, "VERSION     : " + Build.VERSION.SDK_INT);
////            Log.i(APP_NAME, "BOARD       : " + Build.BOARD);
////            Log.i(APP_NAME, "BRAND       : " + Build.BRAND);
//            Log.i(APP_NAME, "CPU_ABI     : " + BUILD_PROPS.getProperty("ro.product.cpu.abi"));
//            Log.i(APP_NAME, "CPU_ABI2    : " + BUILD_PROPS.getProperty("ro.product.cpu.abi2"));
//            Log.i(APP_NAME, "DEVICE      : " + Build.DEVICE);
//            Log.i(APP_NAME, "DISPLAY     : " + Build.DISPLAY);
////            Log.i(APP_NAME, "FINGERPRINT : " + Build.FINGERPRINT);
////            Log.i(APP_NAME, "ID          : " + Build.ID);
////            Log.i(APP_NAME, "MANUFACTURER: " + BUILD_PROPS.getProperty("ro.product.manufacturer"));
////            Log.i(APP_NAME, "MODEL       : " + Build.MODEL);
////            Log.i(APP_NAME, "PRODUCT     : " + Build.PRODUCT);
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
