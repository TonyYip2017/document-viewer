package org.emdev.utils;

import org.json.JSONArray;

import java.util.Collection;

public final class LengthUtils {
  private static final String SAFE_STRING = "";

  private LengthUtils() { }

  public static boolean isEmpty(final String s) { return s == null || s.isEmpty(); }
  public static boolean isEmpty(final Object[] array) { return array == null || array.length == 0; }
  public static boolean isEmpty(final JSONArray array) { return array == null || array.length() == 0; }
  public static boolean isEmpty(final boolean[] array) { return array == null || array.length == 0; }
  public static boolean isEmpty(final Collection<?> c) { return c == null || c.isEmpty(); }

  public static boolean isNotEmpty(final String s) {
    return !isEmpty(s);
  }
  public static boolean isNotEmpty(final Object[] array) { return !isEmpty(array); }
  public static boolean isNotEmpty(final JSONArray array) { return !isEmpty(array); }
  public static boolean isNotEmpty(final boolean[] array) { return !isEmpty(array); }
  public static boolean isNotEmpty(final Collection<?> c) { return !isEmpty(c); }

  public static int length(final Object[] arr) { return arr != null ? arr.length : 0; }
  public static int length(final Collection<?> c) { return c != null ? c.size() : 0; }

  public static String unsafeString(final String original) { return isNotEmpty(original) ? original : null; }

  public static String safeString(final String original) { return safeString(original, SAFE_STRING); }
  public static String safeString(final String original, final String defaultValue) { return isNotEmpty(original) ? original : defaultValue; }

  public static String toString(final Object obj) { return obj != null ? obj.toString() : SAFE_STRING; }
}
