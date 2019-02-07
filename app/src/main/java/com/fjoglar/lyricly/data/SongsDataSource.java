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

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Main entry point for accessing songs data.
 */
public interface SongsDataSource {

    interface RemoteDataSource {

        /**
         * Gets a list of songs from the remote data source.
         *
         * @param limit number of songs to be fetched.
         * @return the list of top songs from the data source.
         */
        List<Track> fetchTopSongs(int limit);

        /**
         * Gets the lyrics of a song from the remote data source.
         *
         * @param artist the artist of the song
         * @param title  the title of the sogn
         * @return the user from the data source.
         */
        String fetchSongLyrics(String artist, String title);
    }

    interface LocalDataSource {

        /**
         * Insert a list of songs to the data source.
         */
        void saveSongs(List<Song> songs);

        /**
         * Gets a song the data source.
         */
        void saveSong(Song song);

        /**
         * Gets the top songs from the data source.
         *
         * @return the songs from the data source.
         */
        Flowable<List<Song>> getTopSongs();

        /**
         * Gets the recent songs from the data source.
         *
         * @return the songs from the data source.
         */
        Flowable<List<Song>> getRecentSongs();

        /**
         * Gets the favorite songs from the data source.
         *
         * @return the songs from the data source.
         */
        Flowable<List<Song>> getFavoriteSongs();

        /**
         * Gets the song from the data source.
         *
         * @param id the id of the song to be getted.
         * @return the song from the data source.
         */
        Single<Song> getSongById(int id);

        /**
         * Gets the top song from the data source.
         *
         * @param napsterId the id of the song to be getted.
         * @return the song from the data source.
         */
        Song getTopSongByNapsterId(String napsterId);

        /**
         * Gets the song from the data source.
         *
         * @param napsterId the id of the song to be getted.
         * @return the song from the data source.
         */
        Song getFavoriteSongByNapsterId(String napsterId);

        /**
         * Updates the order of the selected top song.
         *
         * @param id         id of the song to by updated.
         * @param order      order to set in the song.
         * @param createdAt date to set in the song.
         */
        void updateTopSongOrder(int id, int order, Date createdAt);

        /**
         * Updates the song as favorite.
         *
         * @param song the song to be updated.
         */
        Completable updateFavoriteSong(Song song);

        /**
         * Updates the song from the data source.
         *
         * @param napsterId the id of the song to be updated.
         */
        void updateFavoriteSongByNapsterId(String napsterId);

        /**
         * Deletes all top songs from the data source.
         */
        void deleteTopSongs();

        /**
         * Delete old top songs.
         *
         * @param date limit date of songs.
         */
        void deleteOldTopSongs(Date date);

        /**
         * Deletes a favorite song from the data source.
         *
         * @param song the song to be deleted.
         */
        Completable deleteFavoriteSong(Song song);
    }

    interface PreferencesDataSource {

        /**
         * Gets the last time the top songs were updated.
         *
         * @return the time of the last update in millis.
         */
        long getLastUpdatedTimeInMillis();

        /**
         * Sets the last updated time the top songs where fetched.
         */
        void setLastUpdatedTimeInMillis();
    }
}
