package org.emdev.ui.gl;

/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import javax.microedition.khronos.opengles.GL11;

//
// GLCanvas gives a convenient interface to draw using OpenGL.
//
// When a rectangle is specified in this interface, it means the region
// [x, x+width) * [y, y+height)
//
public interface GLCanvas {

    // --Commented out by Inspection (3/10/21 4:52 PM):LogContext LCTX = LogManager.root().lctx("GLCanvas");

    // Tells GLCanvas the size of the underlying GL surface. This should be
    // called before first drawing and when the size of GL surface is changed.
    // This is called by GLRoot and should not be called by the clients
    // who only want to draw on the GLCanvas. Both width and height must be
    // nonnegative.
    void setSize(int width, int height);

// --Commented out by Inspection START (3/10/21 4:51 PM):
//    // Clear the drawing buffers. This should only be used by GLRoot.
//    void clearBuffer();
// --Commented out by Inspection STOP (3/10/21 4:51 PM)
    void clearBuffer(Paint p);
    void clearBuffer(int color);

    // Sets and gets the current alpha, alpha must be in [0, 1].
    void setAlpha(float alpha);

    float getAlpha();

// --Commented out by Inspection START (3/10/21 4:52 PM):
//    // (current alpha) = (current alpha) * alpha
//    void multiplyAlpha(float alpha);
// --Commented out by Inspection STOP (3/10/21 4:52 PM)

    // Change the current transform matrix.
    void translate(float x, float y, float z);

    void translate(float x, float y);

    void scale(float sx, float sy, float sz);

    void rotate(float angle, float x, float y, float z);

    // --Commented out by Inspection (3/10/21 4:52 PM):void multiplyMatrix(float[] mMatrix, int offset);

    // Pushes the configuration state (matrix, and alpha) onto
    // a private stack.
    void save();

    // Same as save(), but only save those specified in saveFlags.
    void save(int saveFlags);

    int SAVE_FLAG_ALL = 0xFFFFFFFF;
    int SAVE_FLAG_ALPHA = 0x01;
    int SAVE_FLAG_MATRIX = 0x02;

    // Pops from the top of the stack as current configuration state (matrix,
    // alpha, and clip). This call balances a previous call to save(), and is
    // used to remove all modifications to the configuration state since the
    // last save call.
    void restore();

// --Commented out by Inspection START (3/10/21 4:52 PM):
//    // Draws a line using the specified paint from (x1, y1) to (x2, y2).
//    // (Both end points are included).
//    void drawLine(float x1, float y1, float x2, float y2, Paint paint);
// --Commented out by Inspection STOP (3/10/21 4:52 PM)

// --Commented out by Inspection START (3/10/21 4:52 PM):
//    // Draws a rectangle using the specified paint from (x1, y1) to (x2, y2).
//    // (Both end points are included).
//    void drawRect(float x1, float y1, float width, float heught, Paint paint);
// --Commented out by Inspection STOP (3/10/21 4:52 PM)

    // --Commented out by Inspection (3/10/21 4:52 PM):void drawRect(final RectF r, final Paint paint);

    // Fills the specified rectangle with the specified color.
    void fillRect(float x, float y, float width, float height, int color);

    void fillRect(RectF r, Paint p);

    // Draws a texture to the specified rectangle.
    void drawTexture(BasicTexture texture, int x, int y, int width, int height);

    // Draws the source rectangle part of the texture to the target rectangle.
    boolean drawTexture(BasicTexture texture, RectF source, RectF target);

// --Commented out by Inspection START (3/10/21 4:52 PM):
//    // Draw a texture with a specified texture transform.
//    void drawTexture(BasicTexture texture, float[] mTextureTransform, int x, int y, int w, int h);
// --Commented out by Inspection STOP (3/10/21 4:52 PM)

    // Gets the underlying GL instance. This is used only when direct access to
    // GL is needed.
    GL11 getGLInstance();

    // Unloads the specified texture from the canvas. The resource allocated
    // to draw the texture will be released. The specified texture will return
    // to the unloaded state. This function should be called only from
    // BasicTexture or its descendant
    boolean unloadTexture(BasicTexture texture);

// --Commented out by Inspection START (3/10/21 4:52 PM):
//    // Delete the specified buffer object, similar to unloadTexture.
//    void deleteBuffer(int bufferId);
// --Commented out by Inspection STOP (3/10/21 4:52 PM)

    // Delete the textures and buffers in GL side. This function should only be
    // called in the GL thread.
    void deleteRecycledResources();

    void setClipRect(RectF bounds);

    void setClipRect(float x, float y, float w, float h);

    void setClipPath(final PointF... path);

    void clearClipRect();

    void drawPoly(final int color, final PointF... path);

    void fillPoly(final int color, final PointF... path);
}
