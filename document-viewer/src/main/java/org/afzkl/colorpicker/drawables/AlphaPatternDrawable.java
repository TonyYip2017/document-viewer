/*
 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.afzkl.colorpicker.drawables;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * This drawable that draws a simple white and gray chessboard pattern.
 * It's pattern you will often see as a background behind a
 * partly transparent image in many applications.
 *
 * @author Daniel Nilsson
 */
public class AlphaPatternDrawable extends Drawable {
    private final int mRectangleSize;
    private final Paint mPaintWhite = new Paint();
    private final Paint mPaintGray = new Paint();
    private Bitmap mBitmap;

    public AlphaPatternDrawable(final int rectangleSize) {
        mRectangleSize = rectangleSize;
        mPaintWhite.setColor(0xffffffff);
        mPaintGray.setColor(0xffcbcbcb);
    }

    @Override
    public void draw(final Canvas canvas) {
        final Rect bounds = getBounds();
        final int width = bounds.width();
        final int height = bounds.height();

        if (width <= 0 || height <= 0)
            return;
        if (mBitmap == null || mBitmap.isRecycled() || mBitmap.getWidth() != width || mBitmap.getHeight() != height)
            refreshBitmap(width, height);

        canvas.drawBitmap(mBitmap, null, bounds, null);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public void setAlpha(int alpha) { }

    @Override
    public void setColorFilter(ColorFilter cf) { }

//    @Override
//    protected void onBoundsChange(final Rect bounds) {
//        super.onBoundsChange(bounds);
//
//        final int height = bounds.height();
//        final int width = bounds.width();
//
//        numRectanglesHorizontal = (int) Math.ceil((width / mRectangleSize));
//        numRectanglesVertical = (int) Math.ceil(height / mRectangleSize);
//    }

    private void refreshBitmap(final int width, final int height) {
        int numRectanglesHorizontal = width / mRectangleSize;
        int numRectanglesVertical = height / mRectangleSize;

        if (mBitmap != null)
            mBitmap.recycle();
        mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        final Canvas canvas = new Canvas(mBitmap);
        final Rect r = new Rect();
        boolean verticalStartWhite = true;
        for (int i = 0; i <= numRectanglesVertical; i++) {
            boolean isWhite = verticalStartWhite;
            for (int j = 0; j <= numRectanglesHorizontal; j++) {
                r.top = i * mRectangleSize;
                r.left = j * mRectangleSize;
                r.bottom = r.top + mRectangleSize;
                r.right = r.left + mRectangleSize;
                canvas.drawRect(r, isWhite ? mPaintWhite : mPaintGray);
                isWhite = !isWhite;
            }
            verticalStartWhite = !verticalStartWhite;
        }
    }

}
