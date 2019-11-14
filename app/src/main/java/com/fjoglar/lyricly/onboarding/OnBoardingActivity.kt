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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import butterknife.ButterKnife
import com.fjoglar.lyricly.R
import com.fjoglar.lyricly.songs.SongsActivity
import com.fjoglar.lyricly.util.extensions.animateAlpha
import com.fjoglar.lyricly.util.extensions.setAnimatedVectorDrawable
import com.fjoglar.lyricly.util.extensions.showAlertDialogOk
import com.fjoglar.lyricly.util.navigation.Navigator
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private var lastPosition: Int = 0
    private val lastPageIndex: Int?
        get() {
            return view_pager.adapter?.itemCount?.minus(1)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        ButterKnife.bind(this)

        setUpViewPager()
        setUpListeners()
    }

    private fun setUpViewPager() {
        view_pager.adapter = OnBoardingPagerAdapter { view ->
            when (view.id) {
                R.id.btn_on_boarding_enable_notification_access -> {
                    Navigator.showNotificationPermissionSettings(this@OnBoardingActivity)
                }
                R.id.btn_on_boarding_enable_notification_info -> {
                    showAlertDialogOk(
                        this@OnBoardingActivity,
                        R.string.on_boarding_notification_access_more_info_title,
                        R.string.on_boarding_notification_access_more_info
                    )
                }
            }
        }
        view_pager.registerOnPageChangeCallback(onPageChangeCallback)
        ink_page_indicator.setViewPager(view_pager)
    }

    private fun setUpListeners() {
        btn_on_boarding_skip.setOnClickListener {
            view_pager.currentItem = lastPageIndex ?: 0
        }
        btn_on_boarding_continue.setOnClickListener {
            if (view_pager.currentItem == lastPageIndex) {
                launchSongsActivity()
            }
            view_pager.currentItem += 1
        }
    }

    private fun launchSongsActivity() {
        startActivity(SongsActivity.startIntent(this))
        finish()
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == lastPageIndex) {
                btn_on_boarding_skip.animateAlpha(false)
                btn_on_boarding_continue
                    .setAnimatedVectorDrawable(R.drawable.on_boarding_next_to_ok_animated_vector)
            } else if (position == lastPageIndex?.minus(1) && lastPosition == lastPageIndex) {
                btn_on_boarding_skip.animateAlpha(true)
                btn_on_boarding_continue
                    .setAnimatedVectorDrawable(R.drawable.on_boarding_ok_to_next_animated_vector)
            }
            lastPosition = position
        }
    }
}
