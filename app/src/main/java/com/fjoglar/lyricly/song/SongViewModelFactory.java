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

package com.fjoglar.lyricly.song;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fjoglar.lyricly.data.SongsDataSource;

/**
 * Factory for ViewModels
 */
public class SongViewModelFactory implements ViewModelProvider.Factory {

    private final SongsDataSource mSongsDataSource;
    private final int mSongId;

    public SongViewModelFactory(SongsDataSource songsDataSource, int songId) {
        mSongsDataSource = songsDataSource;
        mSongId = songId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SongViewModel.class)) {
            return (T) new SongViewModel(mSongsDataSource, mSongId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
