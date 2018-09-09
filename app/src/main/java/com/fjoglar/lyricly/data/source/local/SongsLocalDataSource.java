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

import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Using the Room database as a data source.
 */
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
    public void saveSongs(List<Song> songs) {
        mSongDatabase.songDao().insertAll(songs);
    }

    @Override
    public void saveSong(Song song) {
        mSongDatabase.songDao().insert(song);
    }

    @Override
    public Flowable<List<Song>> getTopSongs() {
        return mSongDatabase.songDao().getTopSongs();
    }

    @Override
    public Flowable<List<Song>> getRecentSongs() {
        return mSongDatabase.songDao().getRecentSongs();
    }

    @Override
    public Flowable<List<Song>> getFavoriteSongs() {
        return mSongDatabase.songDao().getFavoriteSongs();
    }

    @Override
    public Single<Song> getSongById(int id) {
        return mSongDatabase.songDao().getById(id);
    }

    @Override
    public void updateFavoriteSongById(int id) {
        mSongDatabase.songDao().updateFavoriteSongById(id);
    }

    @Override
    public void deleteTopSongs() {
        mSongDatabase.songDao().deleteTopSongs();
    }

    @Override
    public void deleteFavoriteSongById(int id) {
        mSongDatabase.songDao().deleteFavoriteSongById(id);
    }
}