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

package com.fjoglar.lyricly.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fjoglar.lyricly.data.model.Song;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Data Access Object for the songs table.
 */
@Dao
public interface SongDao {

    /**
     * Insert a list of songs in the database.
     *
     * @param songs the list of songs to be inserted.
     */
    @Insert
    void insertAll(List<Song> songs);

    /**
     * Insert a song in the database. If the song already exists, replaces it.
     *
     * @param song the song to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song song);

    /**
     * Get the top songs from the table.
     *
     * @return the top songs from the table
     */
    @Query("SELECT * FROM songs WHERE top = 1 ORDER BY top_order ASC")
    Flowable<List<Song>> getTopSongs();

    /**
     * Get the recent songs from the table.
     *
     * @return the recent songs from the table
     */
    @Query("SELECT * FROM songs WHERE recent = 1 ORDER BY created_at DESC")
    Flowable<List<Song>> getRecentSongs();

    /**
     * Get the favorite songs from the table.
     * Since the top and recent songs are updated when added to favorites to keep UI simple to
     * update, they are not getted on the favorite list because they will be duplicated.
     *
     * @return the favorite songs from the table
     */
    @Query("SELECT * FROM songs WHERE favorite = 1 AND top = 0 AND recent = 0 ORDER BY id ASC")
    Flowable<List<Song>> getFavoriteSongs();

    /**
     * Get a song from the table.
     *
     * @param songId the song id to be getted.
     * @return the selected song from the table
     */
    @Query("SELECT * FROM songs WHERE id = (:songId)")
    Flowable<Song> getById(int songId);

    /**
     * Get a top song from the table.
     *
     * @return the selected song from the table.
     */
    @Query("SELECT * FROM songs WHERE top = 1 AND source_id = (:sourceId)")
    Song getTopSongByNapsterId(String sourceId);

    /**
     * Get a favorite song from the table.
     *
     * @return the selected song from the table.
     */
    @Query("SELECT * FROM songs WHERE favorite = 1 AND source_id = (:sourceId)")
    Song getFavoriteSongBySourceId(String sourceId);

    /**
     * Get the last recent song from the table.
     *
     * @return the selected song from the table.
     */
    @Query("SELECT * FROM songs WHERE recent = 1 ORDER BY created_at DESC LIMIT 1")
    Song getLastRecentSong();

    /**
     * Updates the order of the selected top song.
     *
     * @param songId id of the song to by updated.
     * @param order  order to set in the song.
     * @param date   date when the song was updated
     */
    @Query("UPDATE songs SET top_order = (:order), created_at = (:date) WHERE id = (:songId)")
    void updateTopSongOrder(int songId, int order, Date date);

    /**
     * Update a song checking or unchecking it as favorite.
     *
     * @param songId     the song id to be updated.
     * @param isFavorite if the song must be updated as favorite.
     */
    @Query("UPDATE songs SET favorite = (:isFavorite) WHERE id = (:songId)")
    void updateFavoriteSongById(int songId, boolean isFavorite);

    /**
     * Update a song checking it from favorite.
     *
     * @param sourceId the song id to be updated.
     */
    @Query("UPDATE songs SET favorite = 1 WHERE top = 1 AND source_id = (:sourceId)")
    void updateTopSongByNapsterId(String sourceId);

    /**
     * Update a song unchecking it from favorite.
     *
     * @param sourceId the song id to be updated.
     */
    @Query("UPDATE songs SET favorite = 0 WHERE source_id = (:sourceId)")
    void removeSongFromFavorite(String sourceId);

    /**
     * Delete all top songs.
     */
    @Query("DELETE FROM songs WHERE top = 1")
    void deleteTopSongs();

    /**
     * Delete old top songs.
     *
     * @param date limit date of songs.
     */
    @Query("DELETE FROM songs WHERE top = 1 AND created_at < (:date)")
    void deleteOldTopSongs(Date date);

    /**
     * Delete the selected favorite song.
     *
     * @param songId the song id to be deleted.
     */
    @Query("DELETE FROM songs WHERE favorite = 1 AND id = (:songId)")
    void deleteFavoriteSongById(int songId);

    /**
     * Delete the selected favorite song.
     *
     * @param sourceId the song id to be deleted.
     */
    @Query("DELETE FROM songs WHERE favorite = 1 AND recent = 0 AND top = 0 AND source_id = (:sourceId)")
    void deleteFavoriteSongByNapsterId(String sourceId);
}
