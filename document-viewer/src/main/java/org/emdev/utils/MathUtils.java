package org.emdev.utils;

import android.graphics.Rect;
import android.graphics.RectF;

public class MathUtils {

    public static int adjust(final int value, final int min, final int max) {
        return Math.min(Math.max(min, value), max);
    }

    public static float adjust(final float value, final float min, final float max) {
        return Math.min(Math.max(min, value), max);
    }

    public static Rect rect(final RectF rect) {
        return new Rect((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
    }

    public static Rect rect(final float left, final float top, final float right, final float bottom) {
        return new Rect((int) left, (int) top, (int) right, (int) bottom);
    }

    public static RectF zoom(final RectF rect, final float zoom) {
        return new RectF(zoom * rect.left, zoom * rect.top, zoom * rect.right, zoom * rect.bottom);
    }

    public static void zoom(final RectF rect, final float zoom, final RectF target) {
        target.left = rect.left * zoom;
        target.right = rect.right * zoom;
        target.top = rect.top * zoom;
        target.bottom = rect.bottom * zoom;
    }

    public static RectF zoom(final Rect rect, final float zoom) {
        return new RectF(zoom * rect.left, zoom * rect.top, zoom * rect.right, zoom * rect.bottom);
    }

    public static Rect zoom(final float left, final float top, final float right, final float bottom, final float zoom) {
        return new Rect((int) (zoom * left), (int) (zoom * top), (int) (zoom * right), (int) (zoom * bottom));
    }


    public static float fmin(final float... values) {
        float min = Float.MAX_VALUE;
        for (final float v : values) {
            min = Math.min(v, min);
        }
        return min;
    }

    public static float fmax(final float... values) {
        float max = Float.MIN_VALUE;
        for (final float v : values) {
            max = Math.max(v, max);
        }
        return max;
    }

    public static float round(final float value, final float share) {
        return (float) Math.floor(value * share) / share;
    }

    public static RectF floor(final RectF rect) {
        rect.left = (float) Math.floor(rect.left);
        rect.top = (float) Math.floor(rect.top);
        rect.right = (float) Math.floor(rect.right);
        rect.bottom = (float) Math.floor(rect.bottom);
        return rect;
    }

    public static RectF round(final RectF rect) {
        rect.left = (float) Math.floor(rect.left);
        rect.top = (float) Math.floor(rect.top);
        rect.right = (float) Math.ceil(rect.right);
        rect.bottom = (float) Math.ceil(rect.bottom);
        return rect;
    }

    public static int nextPowerOf2(int n) {
        if (n <= 0 || n > (1 << 30)) {
            throw new IllegalArgumentException();
        }
        n -= 1;
        n |= n >> 16;
        n |= n >> 8;
        n |= n >> 4;
        n |= n >> 2;
        n |= n >> 1;
        return n + 1;
    }

    public static boolean isOpaque(final int color) {
        return color >>> 24 == 0xFF;
    }
}
