//package org.emdev.common.log;
//
//import android.annotation.SuppressLint;
//
//import org.emdev.BaseDroidApp;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.lang.Thread.UncaughtExceptionHandler;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//class EmergencyHandler implements UncaughtExceptionHandler {
//
//    UncaughtExceptionHandler system = Thread.getDefaultUncaughtExceptionHandler();
//
//    EmergencyHandler() {
//        Thread.setDefaultUncaughtExceptionHandler(this);
//    }
//
//    @Override
//    public void uncaughtException(final Thread thread, final Throwable ex) {
//        BaseDroidApp.logThrowable(ex);
////        processException(ex);
//        system.uncaughtException(thread, ex);
//    }
//
//    void processException(final Throwable ex) {
//        @SuppressLint("SimpleDateFormat")
//        final File file = new File(LogManager.LOG_STORAGE,
//                BaseDroidApp.APP_PACKAGE + "." +
//                     BaseDroidApp.APP_VERSION_NAME + "." +
//                     new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date()) +
//                     ".stacktrace");
//
//        try (PrintWriter writer = new PrintWriter(file)) {
//            writer.print(
//                    "Application information:\n\n" +
//                    BaseDroidApp.APP_INFO + "\n\n" +
//                    "\nError information:\n\n");
//            ex.printStackTrace(writer);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
