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

package com.fjoglar.lyricly.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjoglar.lyricly.R
import kotlinx.android.synthetic.main.on_boarding_item.view.*
import kotlinx.android.synthetic.main.on_boarding_item.view.iv_on_boarding
import kotlinx.android.synthetic.main.on_boarding_permissions_item.view.*

class OnBoardingPagerAdapter(val listener: (view: View) -> Unit) :
    RecyclerView.Adapter<OnBoardingPagerAdapter.OnBoardingViewHolder>() {

    val images = listOf(
        R.drawable.on_boarding_img_lyrics_at_your_hand,
        R.drawable.on_boarding_img_explore_music,
        R.drawable.on_boarding_img_favorite
    )
    val titles =
        listOf(
            R.string.on_boarding_lyrics_at_your_hand_title,
            R.string.on_boarding_explore_music_title,
            R.string.on_boarding_favorite_title
        )
    val subtitles = listOf(
        R.string.on_boarding_lyrics_at_your_hand_subtitle,
        R.string.on_boarding_explore_music_subtitle,
        R.string.on_boarding_favorite_subtitle
    )

    private object ViewType {
        const val ON_BOARDING = 0
        const val PERMISSIONS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val view: View
        return when (viewType) {
            ViewType.ON_BOARDING -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.on_boarding_item, parent, false)
                OnBoardingViewHolder(view)
            }
            ViewType.PERMISSIONS -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.on_boarding_permissions_item, parent, false)
                OnBoardingPermissionViewHolder(view)
            }
            else -> throw IllegalArgumentException("Wrong OnBoardingAdapter ViewType")
        }
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 3) ViewType.ON_BOARDING else ViewType.PERMISSIONS
    }

    open inner class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(position: Int) = with(itemView) {
            iv_on_boarding.setImageResource(images[position])
            tv_on_boarding_title.setText(titles[position])
            tv_on_boarding_subtitle.setText(subtitles[position])
        }
    }

    inner class OnBoardingPermissionViewHolder(itemView: View) :
        OnBoardingPagerAdapter.OnBoardingViewHolder(itemView) {

        override fun bind(position: Int) = with(itemView) {
            btn_on_boarding_enable_notification_access.setOnClickListener {
                listener(it)
            }
            btn_on_boarding_enable_notification_info.setOnClickListener {
                listener(it)
            }
        }
    }
}