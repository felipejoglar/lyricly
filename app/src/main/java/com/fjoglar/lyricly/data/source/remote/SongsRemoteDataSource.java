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

package com.fjoglar.lyricly.data.source.remote;

import android.support.annotation.Nullable;

import com.fjoglar.lyricly.BuildConfig;
import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.RecentlyPlayedSongEntity;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;
import com.fjoglar.lyricly.data.source.remote.api.NapsterService;
import com.fjoglar.lyricly.data.source.remote.api.OvhLyricsService;
import com.fjoglar.lyricly.data.source.remote.entity.NapsterApiResponse;
import com.fjoglar.lyricly.data.source.remote.entity.OvhLyricsApiResponse;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SongsRemoteDataSource implements SongsDataSource {

    @Nullable
    private static SongsRemoteDataSource INSTANCE = null;

    private NapsterService mNapsterService;
    private OvhLyricsService mOvhLyricsService;

    // Prevent direct instantiation.
    private SongsRemoteDataSource() {
        mNapsterService = NapsterService.retrofit().create(NapsterService.class);
        mOvhLyricsService = OvhLyricsService.retrofit().create(OvhLyricsService.class);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link SongsRemoteDataSource} instance
     */
    public static SongsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SongsRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance next
     * time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public List<Track> fetchTopSongs(int limit) {
        List<Track> topSongs = new ArrayList<>();
        Call<NapsterApiResponse> call =
                mNapsterService.getTopTracks(BuildConfig.NAPSTER_API_KEY, limit);
        try {
            topSongs = call.execute().body().getTracks();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topSongs;
    }

    @Override
    public String fetchSongLyrics(String artist, String title) {
        String lyrics = null;
        Call<OvhLyricsApiResponse> call =
                mOvhLyricsService.getSongLyrics(artist, title);
        try {
            lyrics = call.execute().body().getLyrics();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lyrics;
    }

    @Override
    public List<TopSongEntity> getTopSongs() {
        // Not used in local data source.
        return null;
    }

    @Override
    public List<RecentlyPlayedSongEntity> getRecentSongs() {
        // Not used in local data source.
        return null;
    }

    @Override
    public List<FavoriteSongEntity> getFavoriteSongs() {
        // Not used in local data source.
        return null;
    }

    @Override
    public TopSongEntity getTopSongById(int id) {
        // Not used in local data source.
        return null;
    }

    @Override
    public RecentlyPlayedSongEntity getRecentSongById(int id) {
        // Not used in local data source.
        return null;
    }

    @Override
    public FavoriteSongEntity getFavoriteSongById(int id) {
        // Not used in local data source.
        return null;
    }
}
