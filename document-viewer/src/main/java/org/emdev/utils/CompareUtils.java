package org.emdev.utils;

import java.util.Objects;

public final class CompareUtils {
  private CompareUtils() { }

  public static int compare(final int val1, final int val2) { return val1 < val2 ? -1 : val1 > val2 ? 1 : 0; }
  public static int compare(final long val1, final long val2) { return val1 < val2 ? -1 : val1 > val2 ? 1 : 0; }
  public static int compare(final float val1, final float val2) { return val1 < val2 ? -1 : val1 > val2 ? 1 : 0; }

  public static <T extends Comparable<T>> int compare(final T t1, final T t2) {
    return  t1 == null ? (t2 == null ? 0 : -1) :
            t2 == null ? 1 :
            t1.compareTo(t2);
  }

  public static boolean equals(final Object o1, final Object o2) { return Objects.equals(o1, o2); }
  public static boolean equals(final String s1, final String s2) { return LengthUtils.isEmpty(s1) ? LengthUtils.isEmpty(s2) : s1.equals(s2); }
}
