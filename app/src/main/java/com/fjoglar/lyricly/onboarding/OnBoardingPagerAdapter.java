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

package com.fjoglar.lyricly.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjoglar.lyricly.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingPagerAdapter extends RecyclerView.Adapter<OnBoardingPagerAdapter.OnBoardingViewHolder> {

    public OnBoardingPagerAdapter() {
    }

    @NonNull
    @Override
    public OnBoardingPagerAdapter.OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_view, parent, false);
        return new OnBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingPagerAdapter.OnBoardingViewHolder holder, int position) {
        holder.mTextViewPageTitle.setText("Page " + position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_page_title)
        TextView mTextViewPageTitle;

        public OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}