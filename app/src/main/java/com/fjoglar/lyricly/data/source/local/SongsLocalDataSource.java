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

package com.fjoglar.lyricly.data.source.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;

import java.util.List;

public class SongsLocalDataSource implements SongsDataSource.LocalDataSource {

    @Nullable
    private static SongsLocalDataSource INSTANCE = null;

    private final SongDatabase mSongDatabase;

    private SongsLocalDataSource(SongDatabase songDatabase) {
        mSongDatabase = songDatabase;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param songDatabase the app database
     * @return the {@link SongsLocalDataSource} instance
     */
    public static SongsLocalDataSource getInstance(SongDatabase songDatabase) {
        if (INSTANCE == null) {
            INSTANCE = new SongsLocalDataSource(songDatabase);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(SongDatabase)} to create a new instance next
     * time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void saveTopSongs(List<Song> songs) {
        mSongDatabase.topSongDao().insertAll((TopSongEntity) songs);
    }

    @Override
    public void saveTopSong(Song song) {
        mSongDatabase.topSongDao().insert((TopSongEntity) song);
    }

    @Override
    public LiveData<List<TopSongEntity>> getTopSongs() {
        return mSongDatabase.topSongDao().getAll();
    }

    @Override
    public LiveData<TopSongEntity> getTopSongById(int id) {
        return mSongDatabase.topSongDao().getById(id);
    }

    @Override
    public void deleteTopSongs() {
        mSongDatabase.topSongDao().deleteAll();
    }

    @Override
    public void saveRecentSongs(List<Song> songs) {
        mSongDatabase.recentSongDao().insertAll((RecentSongEntity) songs);
    }

    @Override
    public void saveRecentSong(Song song) {
        mSongDatabase.recentSongDao().insert((RecentSongEntity) song);
    }

    @Override
    public LiveData<List<RecentSongEntity>> getRecentSongs() {
        return mSongDatabase.recentSongDao().getAll();
    }

    @Override
    public LiveData<RecentSongEntity> getRecentSongById(int id) {
        return mSongDatabase.recentSongDao().getById(id);
    }

    @Override
    public void deleteRecentSongs() {
        mSongDatabase.recentSongDao().deleteAll();
    }

    @Override
    public void saveFavoriteSongs(List<Song> songs) {
        mSongDatabase.favoriteSongDao().insertAll((FavoriteSongEntity) songs);
    }

    @Override
    public void saveFavoriteSong(Song song) {
        mSongDatabase.favoriteSongDao().insert((FavoriteSongEntity) song);
    }

    @Override
    public LiveData<List<FavoriteSongEntity>> getFavoriteSongs() {
        return mSongDatabase.favoriteSongDao().getAll();
    }

    @Override
    public LiveData<FavoriteSongEntity> getFavoriteSongById(int id) {
        return mSongDatabase.favoriteSongDao().getById(id);
    }

    @Override
    public void deleteFavoriteSongs() {
        mSongDatabase.favoriteSongDao().deleteAll();
    }

    @Override
    public void deleteFavoriteSongById(int id) {
        mSongDatabase.favoriteSongDao().deleteById(id);
    }
}
