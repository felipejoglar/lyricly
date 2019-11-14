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
import com.fjoglar.lyricly.data.source.local.SongsLocalDataSource;
import com.fjoglar.lyricly.data.source.local.preferences.PreferencesDataSource;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class SongsRepository implements SongsDataSource {

    @Nullable
    private static volatile SongsRepository INSTANCE;

    private final SongsLocalDataSource songsLocalDataSource;
    private final SongsRemoteDataSource songsRemoteDataSource;
    private final PreferencesDataSource preferencesDataSource;

    private SongsRepository(SongsLocalDataSource songsLocalDataSource,
                            SongsRemoteDataSource songsRemoteDataSource, PreferencesDataSource preferencesDataSource) {
        this.songsLocalDataSource = songsLocalDataSource;
        this.songsRemoteDataSource = songsRemoteDataSource;
        this.preferencesDataSource = preferencesDataSource;
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
        return songsRemoteDataSource.fetchTopSongs(limit);
    }

    @Override
    public Track searchCurrentlyPlayingSong(String query) {
        return songsRemoteDataSource.searchCurrentlyPlayingSong(query);
    }

    @Override
    public String fetchSongLyrics(String artist, String title) {
        return songsRemoteDataSource.fetchSongLyrics(artist, title);
    }

    @Override
    public void saveSongs(List<Song> songs) {
        songsLocalDataSource.saveSongs(songs);
    }

    @Override
    public void saveSong(Song song) {
        songsLocalDataSource.saveSong(song);
    }

    @Override
    public Flowable<List<Song>> getTopSongs() {
        return songsLocalDataSource.getTopSongs();
    }

    @Override
    public Flowable<List<Song>> getRecentSongs() {
        return songsLocalDataSource.getRecentSongs();
    }

    @Override
    public Flowable<List<Song>> getFavoriteSongs() {
        return songsLocalDataSource.getFavoriteSongs();
    }

    @Override
    public Flowable<Song> getSongById(int id) {
        return songsLocalDataSource.getSongById(id);
    }

    @Override
    public Song getTopSongByNapsterId(String napsterId) {
        return songsLocalDataSource.getTopSongByNapsterId(napsterId);
    }

    @Override
    public Song getFavoriteSongByNapsterId(String napsterId) {
        return songsLocalDataSource.getFavoriteSongByNapsterId(napsterId);
    }

    @Override
    public Song getLastRecentSong() {
        return songsLocalDataSource.getLastRecentSong();
    }

    @Override
    public void updateTopSongOrder(int id, int order, Date createdAt) {
        songsLocalDataSource.updateTopSongOrder(id, order, createdAt);
    }

    @Override
    public Completable updateFavoriteSong(Song song) {
        return songsLocalDataSource.updateFavoriteSong(song);
    }

    @Override
    public void updateFavoriteSongByNapsterId(String napsterId) {
        songsLocalDataSource.updateFavoriteSongByNapsterId(napsterId);
    }

    @Override
    public void deleteTopSongs() {
        songsLocalDataSource.deleteTopSongs();
    }

    @Override
    public void deleteOldTopSongs(Date date) {
        songsLocalDataSource.deleteOldTopSongs(date);
    }

    @Override
    public Completable deleteFavoriteSong(Song song) {
        return songsLocalDataSource.deleteFavoriteSong(song);
    }

    @Override
    public long getLastUpdatedTimeInMillis() {
        return preferencesDataSource.getLastUpdatedTimeInMillis();
    }

    @Override
    public void setLastUpdatedTimeInMillis() {
        preferencesDataSource.setLastUpdatedTimeInMillis();
    }

    @Override
    public boolean hasOnBoardingBeenShown() {
        return preferencesDataSource.hasOnBoardingBeenShown();
    }

    @Override
    public void setHasOnBoardingBeenShown() {
        preferencesDataSource.setHasOnBoardingBeenShown();
    }
}