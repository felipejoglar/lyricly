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

import androidx.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class SongsRepository implements SongsLocalDataSource, SongsRemoteDataSource, PreferencesDataSource {

    @Nullable
    private static volatile SongsRepository INSTANCE;

    private final SongsLocalDataSource mSongsLocalDataSource;
    private final SongsRemoteDataSource mSongsRemoteDataSource;
    private final PreferencesDataSource mPreferencesDataSource;

    private SongsRepository(SongsLocalDataSource songsLocalDataSource,
                            SongsRemoteDataSource songsRemoteDataSource, PreferencesDataSource preferencesDataSource) {
        mSongsLocalDataSource = songsLocalDataSource;
        mSongsRemoteDataSource = songsRemoteDataSource;
        mPreferencesDataSource = preferencesDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param songsLocalDataSource  the device storage data source
     * @param songsRemoteDataSource the local data source
     * @param preferencesDataSource the key-value data source
     * @return the {@link SongsRepository} instance
     */
    public static SongsRepository getInstance(SongsLocalDataSource songsLocalDataSource,
                                              SongsRemoteDataSource songsRemoteDataSource, PreferencesDataSource preferencesDataSource) {
        if (INSTANCE == null) {
            synchronized (SongsRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SongsRepository(songsLocalDataSource, songsRemoteDataSource, preferencesDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link  #getInstance(SongsLocalDataSource, SongsRemoteDataSource, PreferencesDataSource)}
     * to create a new instance next time it's called.
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
    public void saveSongs(List<Song> songs) {
        mSongsLocalDataSource.saveSongs(songs);
    }

    @Override
    public void saveSong(Song song) {
        mSongsLocalDataSource.saveSong(song);
    }

    @Override
    public Flowable<List<Song>> getTopSongs() {
        return mSongsLocalDataSource.getTopSongs();
    }

    @Override
    public Flowable<List<Song>> getRecentSongs() {
        return mSongsLocalDataSource.getRecentSongs();
    }

    @Override
    public Flowable<List<Song>> getFavoriteSongs() {
        return mSongsLocalDataSource.getFavoriteSongs();
    }

    @Override
    public Flowable<Song> getSongById(int id) {
        return mSongsLocalDataSource.getSongById(id);
    }

    @Override
    public Song getTopSongByNapsterId(String napsterId) {
        return mSongsLocalDataSource.getTopSongByNapsterId(napsterId);
    }

    @Override
    public Song getFavoriteSongByNapsterId(String napsterId) {
        return mSongsLocalDataSource.getFavoriteSongByNapsterId(napsterId);
    }

    @Override
    public void updateTopSongOrder(int id, int order, Date createdAt) {
        mSongsLocalDataSource.updateTopSongOrder(id, order, createdAt);
    }

    @Override
    public Completable updateFavoriteSong(Song song) {
        return mSongsLocalDataSource.updateFavoriteSong(song);
    }

    @Override
    public void updateFavoriteSongByNapsterId(String napsterId) {
        mSongsLocalDataSource.updateFavoriteSongByNapsterId(napsterId);
    }

    @Override
    public void deleteTopSongs() {
        mSongsLocalDataSource.deleteTopSongs();
    }

    @Override
    public void deleteOldTopSongs(Date date) {
        mSongsLocalDataSource.deleteOldTopSongs(date);
    }

    @Override
    public Completable deleteFavoriteSong(Song song) {
        return mSongsLocalDataSource.deleteFavoriteSong(song);
    }

    @Override
    public long getLastUpdatedTimeInMillis() {
        return mPreferencesDataSource.getLastUpdatedTimeInMillis();
    }

    @Override
    public void setLastUpdatedTimeInMillis() {
        mPreferencesDataSource.setLastUpdatedTimeInMillis();
    }
}