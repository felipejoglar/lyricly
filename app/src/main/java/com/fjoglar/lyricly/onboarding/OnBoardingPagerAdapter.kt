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
import kotlinx.android.synthetic.main.page_view.view.*

class OnBoardingPagerAdapter : RecyclerView.Adapter<OnBoardingPagerAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.page_view, parent, false)
        return OnBoardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 3
    }

    class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) = with(itemView) {
            tv_page_title.text = "Page $position"
        }
    }
}