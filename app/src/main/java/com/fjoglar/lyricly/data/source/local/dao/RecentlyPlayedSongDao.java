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
import android.arch.persistence.room.Query;

import com.fjoglar.lyricly.data.source.local.entity.RecentlyPlayedSongEntity;

import java.util.List;

@Dao
public interface RecentlyPlayedSongDao {

    @Insert
    void insertAll(RecentlyPlayedSongEntity... recentlyPlayedSongEntities);

    @Insert
    void insert(RecentlyPlayedSongEntity recentlyPlayedSongEntity);

    @Query("SELECT * FROM recent_songs")
    List<RecentlyPlayedSongEntity> getAll();

    @Query("SELECT * FROM recent_songs WHERE id = (:recentSongId)")
    RecentlyPlayedSongEntity getById(int recentSongId);

    @Query("DELETE FROM recent_songs")
    void deleteAll();
}
