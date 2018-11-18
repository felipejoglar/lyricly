/*
 * Copyright 2018 Felipe Joglar Santos
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

package com.fjoglar.lyricly.songs.favorite;

import android.arch.lifecycle.ViewModelProviders;

import com.fjoglar.lyricly.songs.SongsFragment;
import com.fjoglar.lyricly.songs.SongsViewModelFactory;

public class FavoriteSongsFragment extends SongsFragment {

    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    public static FavoriteSongsFragment newInstance() {
        return new FavoriteSongsFragment();
    }

    @Override
    protected void initViewModel(SongsViewModelFactory songsViewModelFactory) {
        mViewModel = ViewModelProviders.of(this, songsViewModelFactory)
                .get(FavoriteSongsViewModel.class);
    }
}
