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

package com.fjoglar.lyricly.util;

import android.content.Context;

import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.source.local.SongsLocalRepository;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.local.preferences.PreferencesRepository;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteRepository;
import com.fjoglar.lyricly.song.SongViewModelFactory;
import com.fjoglar.lyricly.songs.SongsViewModelFactory;

/**
 * Enables injection of data sources.
 */
public class Injection {

    public static SongsViewModelFactory provideSongsViewModelFactory(Context context) {
        SongsDataSource songsDataSource = provideSongsRepository(context);
        return new SongsViewModelFactory(songsDataSource);
    }

    public static SongViewModelFactory provideSongViewModelFactory(Context context, int songId) {
        SongsDataSource songsDataSource = provideSongsRepository(context);
        return new SongViewModelFactory(songsDataSource, songId);
    }

    private static SongsRemoteDataSource provideSongsRemoteDataSource() {
        return SongsRemoteRepository.getInstance();
    }

    private static SongsLocalRepository provideSongsLocalDataSource(Context context) {
        SongDatabase database = SongDatabase.getInstance(context);
        return SongsLocalRepository.getInstance(database);
    }

    private static PreferencesRepository providePreferencesLocalDataSource(Context context) {
        return PreferencesRepository.getInstance(context);
    }

    private static SongsDataSource provideSongsRepository(Context context) {
        SongsRemoteDataSource remoteDataSource = provideSongsRemoteDataSource();
        SongsLocalRepository localDataSource = provideSongsLocalDataSource(context);
        PreferencesRepository preferencesDataSource = providePreferencesLocalDataSource(context);
        return SongsRepository.getInstance(localDataSource, remoteDataSource, preferencesDataSource);
    }
}