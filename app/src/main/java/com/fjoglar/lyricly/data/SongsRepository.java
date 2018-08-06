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
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.List;

public class SongsRepository implements SongsDataSource.LocalDataSource,
        SongsDataSource.RemoteDataSource {

    @Nullable
    private static SongsRepository INSTANCE = null;

    private final SongsDataSource.LocalDataSource mSongsLocalDataSource;
    private final SongsDataSource.RemoteDataSource mSongsRemoteDataSource;

    private SongsRepository(SongsDataSource.LocalDataSource songsLocalDataSource,
                            SongsDataSource.RemoteDataSource songsRemoteDataSource) {
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
    public static SongsRepository getInstance(SongsDataSource.LocalDataSource songsLocalDataSource,
                                              SongsDataSource.RemoteDataSource songsRemoteDataSource) {
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
    public void saveTopSongs(List<Song> songs) {
        mSongsLocalDataSource.saveTopSongs(songs);
    }

    @Override
    public void saveTopSong(Song song) {
        mSongsLocalDataSource.saveTopSong(song);
    }

    @Override
    public LiveData<List<TopSongEntity>> getTopSongs() {
        return mSongsLocalDataSource.getTopSongs();
    }

    @Override
    public LiveData<TopSongEntity> getTopSongById(int id) {
        return mSongsLocalDataSource.getTopSongById(id);
    }

    @Override
    public void deleteTopSongs() {
        mSongsLocalDataSource.deleteTopSongs();
    }

    @Override
    public void saveRecentSongs(List<Song> songs) {
        mSongsLocalDataSource.saveRecentSongs(songs);
    }

    @Override
    public void saveRecentSong(Song song) {
        mSongsLocalDataSource.saveRecentSong(song);
    }

    @Override
    public LiveData<List<RecentSongEntity>> getRecentSongs() {
        return mSongsLocalDataSource.getRecentSongs();
    }

    @Override
    public LiveData<RecentSongEntity> getRecentSongById(int id) {
        return mSongsLocalDataSource.getRecentSongById(id);
    }

    @Override
    public void deleteRecentSongs() {
        mSongsLocalDataSource.deleteRecentSongs();
    }

    @Override
    public void saveFavoriteSongs(List<Song> songs) {
        mSongsLocalDataSource.saveFavoriteSongs(songs);
    }

    @Override
    public void saveFavoriteSong(Song song) {
        mSongsLocalDataSource.saveFavoriteSong(song);
    }

    @Override
    public LiveData<List<FavoriteSongEntity>> getFavoriteSongs() {
        return mSongsLocalDataSource.getFavoriteSongs();
    }

    @Override
    public LiveData<FavoriteSongEntity> getFavoriteSongById(int id) {
        return mSongsLocalDataSource.getFavoriteSongById(id);
    }

    @Override
    public void deleteFavoriteSongs() {
        mSongsLocalDataSource.deleteFavoriteSongs();
    }

    @Override
    public void deleteFavoriteSongById(int id) {
        mSongsLocalDataSource.deleteFavoriteSongById(id);
    }

    @Override
    public List<FavoriteSongEntity> getWidgetSongs() {
        return mSongsLocalDataSource.getWidgetSongs();
    }
}
