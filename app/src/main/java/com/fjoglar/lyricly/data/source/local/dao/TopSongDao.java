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

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;

import java.util.List;

@Dao
public interface TopSongDao {

    @Insert
    void insertAll(TopSongEntity... topSongEntities);

    @Insert
    void insert(TopSongEntity topSongEntity);

    @Query("SELECT * FROM top_songs")
    LiveData<List<TopSongEntity>> getAll();

    @Query("SELECT * FROM top_songs WHERE id = (:topSongId)")
    LiveData<TopSongEntity> getById(int topSongId);

    @Query("DELETE FROM top_songs")
    void deleteAll();
}