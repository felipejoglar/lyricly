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

package com.fjoglar.lyricly.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentlyPlayedSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.List;

public class SongsRepository implements SongsDataSource {

    @Nullable
    private static SongsRepository INSTANCE = null;

    @NonNull
    private final SongsDataSource mSongsLocalDataSource;

    @NonNull
    private final SongsDataSource mSongsRemoteDataSource;

    private SongsRepository(@NonNull SongsDataSource songsLocalDataSource,
                            @NonNull SongsDataSource songsRemoteDataSource) {
        mSongsLocalDataSource = songsLocalDataSource;
        mSongsRemoteDataSource = songsRemoteDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param songsLocalDataSource  the backend data source
     * @param songsRemoteDataSource the device storage data source
     * @return the {@link SongsRepository} instance
     */
    public static SongsRepository getInstance(@NonNull SongsDataSource songsLocalDataSource,
                                              @NonNull SongsDataSource songsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SongsRepository(songsLocalDataSource, songsRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(SongsDataSource, SongsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public List<Track> fetchTopSongs(int limit) {
        return mSongsRemoteDataSource.fetchTopSongs(limit);
    }

    @Override
    public String fetchSongLyrics(String artist, String title) {
        return mSongsRemoteDataSource.fetchSongLyrics(artist, title);
    }

    @Override
    public LiveData<List<TopSongEntity>> getTopSongs() {
        return mSongsLocalDataSource.getTopSongs();
    }

    @Override
    public LiveData<List<RecentlyPlayedSongEntity>> getRecentSongs() {
        return mSongsLocalDataSource.getRecentSongs();
    }

    @Override
    public LiveData<List<FavoriteSongEntity>> getFavoriteSongs() {
        return mSongsLocalDataSource.getFavoriteSongs();
    }

    @Override
    public LiveData<TopSongEntity> getTopSongById(int id) {
        return mSongsLocalDataSource.getTopSongById(id);
    }

    @Override
    public LiveData<RecentlyPlayedSongEntity> getRecentSongById(int id) {
        return mSongsLocalDataSource.getRecentSongById(id);
    }

    @Override
    public LiveData<FavoriteSongEntity> getFavoriteSongById(int id) {
        return mSongsLocalDataSource.getFavoriteSongById(id);
    }

    @Override
    public void saveTopSong(Song song) {
        mSongsLocalDataSource.saveTopSong(song);
    }

    @Override
    public void deleteTopSongs() {
        mSongsLocalDataSource.deleteTopSongs();
    }
}
