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

package com.fjoglar.lyricly.songs;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;
import com.fjoglar.lyricly.songs.favorite.FavoriteSongsViewModel;
import com.fjoglar.lyricly.songs.recent.RecentSongsViewModel;
import com.fjoglar.lyricly.songs.top.TopSongsViewModel;

/**
 * Factory for ViewModels
 */
public class SongsViewModelFactory implements ViewModelProvider.Factory {

    private final SongsRepository mSongsRepository;
    private SongsRemoteDataSource mSongsRemoteDataSource;

    public SongsViewModelFactory(SongsRepository songsRepository, SongsRemoteDataSource songsRemoteDataSource) {
        mSongsRepository = songsRepository;
        mSongsRemoteDataSource = songsRemoteDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TopSongsViewModel.class)) {
            return (T) new TopSongsViewModel(mSongsRepository, mSongsRemoteDataSource);
        } else if (modelClass.isAssignableFrom(RecentSongsViewModel.class)) {
            return (T) new RecentSongsViewModel(mSongsRepository);
        } else if (modelClass.isAssignableFrom(FavoriteSongsViewModel.class)) {
            return (T) new FavoriteSongsViewModel(mSongsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
