package org.ebookdroid;

import org.emdev.common.log.LogContext;
import org.emdev.common.log.LogManager;

public class EBookDroidLibraryLoader {
    private static final LogContext LCTX = LogManager.root().lctx("LibraryLoader");
    private static boolean loaded = false;

    public static void load() {
        if (loaded)
            return;
        try {
            System.loadLibrary("ebookdroid");
            loaded = true;
        } catch (Throwable th) {
            LCTX.e("Native library cannot be loaded: ", th);
            throw th;
        }
    }

    public static native void free();
}
