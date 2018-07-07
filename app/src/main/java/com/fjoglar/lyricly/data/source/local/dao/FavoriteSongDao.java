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

import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;

import java.util.List;

@Dao
public interface FavoriteSongDao {

    @Insert
    void insertAll(FavoriteSongEntity... favoriteSongEntities);

    @Insert
    void insert(FavoriteSongEntity favoriteSongEntity);

    @Query("SELECT * FROM favorite_songs")
    List<FavoriteSongEntity> getAll();

    @Query("SELECT * FROM favorite_songs WHERE id = (:favoriteSongId)")
    FavoriteSongEntity getById(int favoriteSongId);

    @Query("DELETE FROM favorite_songs")
    void deleteAll();
}
