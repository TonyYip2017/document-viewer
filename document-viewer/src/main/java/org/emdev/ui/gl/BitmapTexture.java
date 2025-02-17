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

package org.emdev.ui.gl;

import android.graphics.Bitmap;

// BitmapTexture is a texture whose content is specified by a fixed Bitmap.
//
// The texture does not own the Bitmap. The user should make sure the Bitmap
// is valid during the texture's lifetime. When the texture is recycled, it
// does not free the Bitmap.
public class BitmapTexture extends UploadedTexture {

    protected Bitmap mContentBitmap;

    public BitmapTexture(final Bitmap bitmap) {
        super();
        if (bitmap != null && !bitmap.isRecycled()) {
            mContentBitmap = bitmap;
        }
    }

    @Override
    protected void onFreeBitmap(final Bitmap bitmap) {
        // Do nothing.
    }

    @Override
    protected Bitmap onGetBitmap() {
        return mContentBitmap;
    }

// --Commented out by Inspection START (3/10/21 4:35 PM):
//    public Bitmap getBitmap() {
//        return mContentBitmap;
//    }
// --Commented out by Inspection STOP (3/10/21 4:35 PM)
}
