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

package com.fjoglar.lyricly.data.source.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.fjoglar.lyricly.data.source.local.converter.DateConverter;
import com.fjoglar.lyricly.data.source.local.dao.FavoriteSongDao;
import com.fjoglar.lyricly.data.source.local.dao.RecentlyPlayedSongDao;
import com.fjoglar.lyricly.data.source.local.dao.TopSongDao;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentlyPlayedSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;

@Database(entities = {TopSongEntity.class, RecentlyPlayedSongEntity.class, FavoriteSongEntity.class},
        version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SongDatabase extends RoomDatabase {

    private static final String DB_NAME = "song.db";
    private static SongDatabase INSTANCE;

    /**
     * Returns the single instance of this class, creating it if necessary.
     */
    public static SongDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SongDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it is accessed for the first time.
     */
    private static SongDatabase buildDatabase(final Context applicationContext) {
        return Room
                .databaseBuilder(applicationContext, SongDatabase.class, DB_NAME)
                .build();
    }

    /**
     * Used to force {@link #getInstance(Context)} to create a new instance next
     * time it's called.
     */
    public static void destroyInstance() {
        if (INSTANCE.isOpen() && INSTANCE != null) {
            INSTANCE.close();
        }
        INSTANCE = null;
    }

    public abstract TopSongDao topSongDao();

    public abstract RecentlyPlayedSongDao recentlyPlayedSongDao();

    public abstract FavoriteSongDao favoriteSongDao();
}
