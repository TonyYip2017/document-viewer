package org.ebookdroid.droids.djvu.codec;

import org.ebookdroid.EBookDroidLibraryLoader;
import org.ebookdroid.core.codec.AbstractCodecContext;

public class DjvuContext extends AbstractCodecContext {

    public static final int DJVU_FEATURES = FEATURE_CACHABLE_PAGE_INFO | FEATURE_PARALLEL_PAGE_ACCESS
            | FEATURE_DOCUMENT_TEXT_SEARCH | FEATURE_EMBEDDED_OUTLINE | FEATURE_CROP_SUPPORT | FEATURE_SPLIT_SUPPORT;

    // --Commented out by Inspection (3/10/21 4:46 PM):private static final LogContext LCTX = LogManager.root().lctx("Djvu");

    static {
        EBookDroidLibraryLoader.load();
    }

    public DjvuContext() {
        super(create(), DJVU_FEATURES);
    }

    @Override
    public DjvuDocument openDocument(final String fileName, final String password) {
        return new DjvuDocument(this, fileName);
    }

    @Override
    protected void freeContext() {
        try {
            free(getContextHandle());
        } catch (Throwable ignored) {
        }
    }

    private static native long create();

    private static native void free(long contextHandle);
}
