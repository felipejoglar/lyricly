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

package com.fjoglar.lyricly.core.extensions

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

fun View.animateAlpha(animateToVisible: Boolean) {
    alpha = if (animateToVisible) 0.0f else 1.0f
    visibility = View.VISIBLE

    with(animate()) {
        alpha(if (animateToVisible) 1.0f else 0.0f)
        interpolator = AccelerateDecelerateInterpolator()
        duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        withEndAction { visibility = if (animateToVisible) View.VISIBLE else View.GONE }
    }.start()
}

fun ImageView.setAnimatedVectorDrawable(animatedVectorDrawableResId: Int) {
    val avd: AnimatedVectorDrawableCompat? =
        AnimatedVectorDrawableCompat.create(context, animatedVectorDrawableResId)

    setImageDrawable(avd)
    avd?.start()
}