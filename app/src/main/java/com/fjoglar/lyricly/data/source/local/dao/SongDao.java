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

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.fjoglar.lyricly.data.model.Song;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
    @Query("SELECT * FROM songs WHERE top = 1")
    Flowable<List<Song>> getTopSongs();

    /**
     * Get the recent songs from the table.
     *
     * @return the recent songs from the table
     */
    @Query("SELECT * FROM songs WHERE recent = 1")
    Flowable<List<Song>> getRecentSongs();

    /**
     * Get the favorite songs from the table.
     * Since the top and recent songs are updated when added to favorites to keep UI simple to
     * update, they are not getted on the favorite list because they will be duplicated.
     *
     * @return the favorite songs from the table
     */
    @Query("SELECT * FROM songs WHERE favorite = 1 AND top = 0 AND recent = 0")
    Flowable<List<Song>> getFavoriteSongs();

    /**
     * Get a song from the table.
     *
     * @param songId the song id to be getted.
     * @return the selected song from the table
     */
    @Query("SELECT * FROM songs WHERE id = (:songId)")
    Single<Song> getById(int songId);

    /**
     * Update a song checking it as favorite.
     *
     * @param songId the song id to be updated.
     */
    @Query("UPDATE songs SET favorite = 1 WHERE id = (:songId)")
    void updateFavoriteSongById(int songId);

    /**
     * Delete all top songs.
     */
    @Query("DELETE FROM songs WHERE top = 1")
    void deleteTopSongs();

    /**
     * Delete the selected favorite song.
     *
     * @param songId the song id to be delete.
     */
    @Query("DELETE FROM songs WHERE favorite = 1 AND id = (:songId)")
    void deleteFavoriteSongById(int songId);
}
