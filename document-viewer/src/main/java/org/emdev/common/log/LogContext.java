package org.emdev.common.log;

import android.util.Log;

public class LogContext {

    private static final String SEPARATOR = ".";

    private final LogContext parent;

    private final String tag;

    private Boolean debugEnabled;

    LogContext(final String tag, final boolean debugEnabled) {
        this.parent = null;
        this.tag = tag;
        this.debugEnabled = debugEnabled;
    }

    private LogContext(final LogContext parent, final String tag) {
        this.parent = parent;
        this.tag = (parent != null ? parent.tag + SEPARATOR : "") + tag;
    }

    public LogContext lctx(final String tag) {
        return new LogContext(this, tag);
    }

    public LogContext lctx(final String tag, final boolean debugEnabled) {
        final LogContext lctx = new LogContext(this, tag);
        lctx.setDebugEnabled(debugEnabled);
        return lctx;
    }

    public void d(final String msg) {
        Log.d(tag, msg);
    }

// --Commented out by Inspection START (3/10/21 5:34 PM):
//    public void d(final String msg, final Throwable th) {
//        Log.d(tag, msg, th);
//    }
// --Commented out by Inspection STOP (3/10/21 5:34 PM)

    public void i(final String msg) {
        Log.i(tag, msg);
    }

// --Commented out by Inspection START (3/10/21 5:34 PM):
//    public void i(final String msg, final Throwable th) {
//        Log.i(tag, msg, th);
//    }
// --Commented out by Inspection STOP (3/10/21 5:34 PM)

    public void w(final String msg) {
        Log.w(tag, msg);
    }

// --Commented out by Inspection START (3/10/21 5:34 PM):
//    public void w(final String msg, final Throwable th) {
//        Log.w(tag, msg, th);
//    }
// --Commented out by Inspection STOP (3/10/21 5:34 PM)

    public void e(final String msg) {
        Log.e(tag, msg);
    }

    public void e(final String msg, final Throwable th) {
        Log.e(tag, msg, th);
    }

    public boolean isDebugEnabled() {
        return debugEnabled != null ? debugEnabled : parent != null && parent.isDebugEnabled();
    }

    public void setDebugEnabled(final boolean enabled) {
        debugEnabled = enabled;
    }

    @Override
    public String toString() {
        return tag;
    }
}
