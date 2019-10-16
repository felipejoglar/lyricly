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

import androidx.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Using the Room database as a data source.
 */
public class SongsLocalRepository implements SongsLocalDataSource {

    @Nullable
    private static volatile SongsLocalRepository INSTANCE;

    private final SongDatabase mSongDatabase;

    private SongsLocalRepository(SongDatabase songDatabase) {
        mSongDatabase = songDatabase;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param songDatabase the app database
     * @return the {@link SongsLocalRepository} instance
     */
    public static SongsLocalRepository getInstance(SongDatabase songDatabase) {
        if (INSTANCE == null) {
            synchronized (SongsLocalRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SongsLocalRepository(songDatabase);
                }
            }
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
    public Flowable<Song> getSongById(int id) {
        return mSongDatabase.songDao().getById(id);
    }

    @Override
    public Song getTopSongByNapsterId(String napsterId) {
        return mSongDatabase.songDao().getTopSongByNapsterId(napsterId);
    }

    @Override
    public Song getFavoriteSongByNapsterId(String napsterId) {
        return mSongDatabase.songDao().getFavoriteSongBySourceId(napsterId);
    }

    @Override
    public Song getLastRecentSong() {
        return mSongDatabase.songDao().getLastRecentSong();
    }

    @Override
    public void updateTopSongOrder(int id, int order, Date createdAt) {
        mSongDatabase.songDao().updateTopSongOrder(id, order, createdAt);
    }

    @Override
    public Completable updateFavoriteSong(Song song) {
        return Completable.fromAction(() -> {
            mSongDatabase.songDao().insert(new Song().createFavoriteSong(song));
            mSongDatabase.songDao().updateFavoriteSongById(song.getId(), true);
        });
    }

    @Override
    public void updateFavoriteSongByNapsterId(String napsterId) {
        mSongDatabase.songDao().updateTopSongByNapsterId(napsterId);
    }

    @Override
    public void deleteTopSongs() {
        mSongDatabase.songDao().deleteTopSongs();
    }

    @Override
    public void deleteOldTopSongs(Date date) {
        mSongDatabase.songDao().deleteOldTopSongs(date);
    }

    @Override
    public Completable deleteFavoriteSong(Song song) {
        if (song.isFavorite() && !(song.isRecent() || song.isTop())) {
            return Completable.fromAction(() -> {
                mSongDatabase.songDao().deleteFavoriteSongById(song.getId());
                mSongDatabase.songDao().removeSongFromFavorite(song.getSourceId());
            });
        } else {
            return Completable.fromAction(() -> {
                mSongDatabase.songDao().updateFavoriteSongById(song.getId(), false);
                mSongDatabase.songDao().deleteFavoriteSongByNapsterId(song.getSourceId());
            });
        }
    }
}