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

import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

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
     * Used to force {@link #getInstance(LocalDataSource, RemoteDataSource)} to create a new
     * instance next time it's called.
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
    public Completable saveSong(Song song) {
        return mSongsLocalDataSource.saveSong(song);
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
    public Single<Song> getSongById(int id) {
        return mSongsLocalDataSource.getSongById(id);
    }

    @Override
    public Completable updateFavoriteSong(Song song) {
        return mSongsLocalDataSource.updateFavoriteSong(song);
    }

    @Override
    public void deleteTopSongs() {
        mSongsLocalDataSource.deleteTopSongs();
    }

    @Override
    public Completable deleteFavoriteSongById(int id) {
        return mSongsLocalDataSource.deleteFavoriteSongById(id);
    }
}