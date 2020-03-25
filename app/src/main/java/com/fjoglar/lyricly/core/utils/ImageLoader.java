/*
 * Copyright 2019 Felipe Joglar Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fjoglar.lyricly.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class ImageLoader {

    public static final String IMAGE_SIZE_BIG = "500x500";
    public static final String IMAGE_SIZE_SMALL = "70x70";

    private ImageLoader() {
    }

    /**
     * Loads thumbnail and then replaces it with full image.
     */
    public static void loadWithThumb(ImageView image, String imageUrl, String thumbUrl) {
        final RequestOptions options = new RequestOptions()
                .override(Target.SIZE_ORIGINAL)
                .dontTransform();

        final RequestBuilder<Drawable> thumbRequest = Glide.with(image)
                .load(thumbUrl)
                .apply(options);

        Glide.with(image)
                .load(imageUrl)
                .apply(options)
                .thumbnail(thumbRequest)
                .placeholder(UiUtil.getPlaceHolderColor())
                .into(image);
    }

    /**
     * Load a resource from network
     *
     * @param context      Android Context
     * @param imageUrl     network image URL
     * @param cornerRadius image corner radius (in dp)
     * @param listener     OnImageLoadedListener that will handle the result
     */
    public static void loadBitmapFromNetwork(Context context,
                                             String imageUrl,
                                             int cornerRadius,
                                             OnImageLoadedListener listener) {
        final RequestOptions options =
                RequestOptions.bitmapTransform(new RoundedCorners(UiUtil.dpToPx(context, cornerRadius)));

        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        if (listener != null) {
                            listener.onResourceReady(resource);
                        }
                    }
                });
    }

    public interface OnImageLoadedListener {
        void onResourceReady(Bitmap bitmap);
    }
}
