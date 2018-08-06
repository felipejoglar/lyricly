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

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.List;

/**
 * Main entry point for accessing songs data.
 */
public interface SongsDataSource {

    interface RemoteDataSource {
        List<Track> fetchTopSongs(int limit);

        String fetchSongLyrics(String artist, String title);
    }

    interface LocalDataSource {
        void saveTopSongs(List<Song> songs);

        void saveTopSong(Song song);

        LiveData<List<TopSongEntity>> getTopSongs();

        LiveData<TopSongEntity> getTopSongById(int id);

        void deleteTopSongs();

        void saveRecentSongs(List<Song> songs);

        void saveRecentSong(Song song);

        LiveData<List<RecentSongEntity>> getRecentSongs();

        LiveData<RecentSongEntity> getRecentSongById(int id);

        void deleteRecentSongs();

        void saveFavoriteSongs(List<Song> songs);

        void saveFavoriteSong(Song song);

        LiveData<List<FavoriteSongEntity>> getFavoriteSongs();

        LiveData<FavoriteSongEntity> getFavoriteSongById(int id);

        void deleteFavoriteSongs();

        void deleteFavoriteSongById(int id);

        List<FavoriteSongEntity> getWidgetSongs();
    }
}
