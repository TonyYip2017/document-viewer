package org.ebookdroid.core.codec;


public interface CodecFeatures {

    // --Commented out by Inspection (3/10/21 4:25 PM):int FEATURE_UNIFIED_PAGE_INTO = 1;
    int FEATURE_CACHABLE_PAGE_INFO = 1 << 1;
    int FEATURE_PARALLEL_PAGE_ACCESS = 1 << 2;

    int FEATURE_DOCUMENT_TEXT_SEARCH = 1 << 3;
//    int FEATURE_DOCUMENT_TEXT_ACCESS = 1 << 4;

    int FEATURE_PAGE_TEXT_SEARCH = 1 << 5;
//    int FEATURE_PAGE_TEXT_ACCESS = 1 << 6;

    int FEATURE_TEXT_SEARCH = FEATURE_DOCUMENT_TEXT_SEARCH | FEATURE_PAGE_TEXT_SEARCH;
    // --Commented out by Inspection (3/10/21 4:24 PM):int FEATURE_TEXT_ACCESS = FEATURE_DOCUMENT_TEXT_ACCESS | FEATURE_PAGE_TEXT_ACCESS;

    // --Commented out by Inspection (3/10/21 4:24 PM):int FEATURE_EMBEDDED_BOOK_INFO = 1 << 7;
    // --Commented out by Inspection (3/10/21 4:24 PM):int FEATURE_EMBEDDED_COVER = 1 << 8;
    int FEATURE_EMBEDDED_OUTLINE = 1 << 9;

    int FEATURE_POSITIVE_IMAGES_IN_NIGHT_MODE = 1 << 10;

    int FEATURE_CROP_SUPPORT = 1 << 11;
    int FEATURE_SPLIT_SUPPORT = 1 << 12;

    boolean isFeatureSupported(int feature);
}
