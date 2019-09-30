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

package com.fjoglar.lyricly.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideHelper {

    public static final String IMAGE_SIZE_BIG = "500x500";
    public static final String IMAGE_SIZE_MEDIUM = "200x200";
    public static final String IMAGE_SIZE_SMALL = "70x70";

    private GlideHelper() {
    }

    /**
     * Loads thumbnail and then replaces it with full image.
     */
    public static void loadWithThumb(ImageView image, String imageUrl, String thumbUrl) {
        // We don't want Glide to crop or resize our image
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
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
}
